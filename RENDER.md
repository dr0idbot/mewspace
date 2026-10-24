# RENDER.md — hosting mewspace-ui on Render

How to deploy the UI module on [Render](https://render.com) (Docker runtime).

## What gets deployed

The repo root `Dockerfile` builds all three modules (`mewspace-backend`,
`mewspace-views`, `mewspace-ui`) and packs only the bootable `mewspace-ui` jar into a
JRE-only runtime image:

- Build stage: `maven:3.9-eclipse-temurin-25-noble` — full reactor build including the
  Vaadin **production** frontend bundle (the `build-frontend` goal runs during `package`),
  so the runtime image never needs Maven/Node.
- Runtime stage: `eclipse-temurin:25-jre-noble`, non-root `ubuntu` user, exposes port 8080,
  and runs with `MaxRAMPercentage=80` + `ExitOnOutOfMemoryError` (OOMs exit so Render
  restarts the container).

The app binds `${PORT:8080}` (Render injects `PORT`), and
`server.forward-headers-strategy=native` is set so HTTPS/TLS is honored behind Render's
edge proxy (correct `https://` URLs and working push/websockets).

## Steps — Dashboard

1. New → **Web Service** → connect the GitHub repo.
2. **Branch**: `main`.
3. **Runtime**: **Docker**; Dockerfile path `./Dockerfile`; Docker context `./`
   (the root must be the context — the reactor needs the parent POM and all modules).
4. **Plan**: Free works for demos (512 MB; spins down after ~15 min idle, cold start is
   a container boot, not a Maven build). Starter+ for always-on.
5. **Region**: closest to your users.
6. Health check path: `/` (the Vaadin root route).
7. Auto-deploy: on push to selected branch.

## render.yaml alternative

```yaml
services:
  - type: web
    name: mewspace-ui
    runtime: docker
    dockerfilePath: ./Dockerfile
    dockerContext: ./
    branch: main
    healthCheckPath: /
    plan: free
    autoDeploy: true
```

## Environment variables

None required. `mewspace.api.*` defaults (countries.dev, `https://countries.dev`) work as-is.
Optional overrides: `mewspace.api.client.connect-timeout` / `mewspace.api.client.read-timeout`.

## Local smoke test before deploying

```bash
docker build -t mewspace-ui .
docker run --rm -p 8080:8080 mewspace-ui     # or -e PORT=10000 -p 10000:10000
```

## Gotchas

- First build takes ~5–10 min (Maven downloads + npm + frontend compile). Rebuilds with
  unchanged POMs reuse the dependency layers.
- `.dockerignore` keeps `target/`, generated frontend/bundle dirs and local docs out of
  the build context.
- The countries.dev API is called server-side, so no CORS set-up is needed.
- Free-plan spin-down: the first visitor after idle time waits for the container to boot
  (~1 min including Vaadin-side init).
