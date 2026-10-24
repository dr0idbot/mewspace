# mewspace

A multi-module Maven application built with **Java 25**, **Vaadin 25 (pure Flow, no Hilla)** and
**Spring Boot 4.1.1** — currently serving as a countries explorer plus in-app UI playground
(theme control, icon gallery).

## Features

- **Countries grid** (`@Route("")`) — every record from [countries.dev](https://countries.dev)
  as a sortable, resizable, frozen-name grid backed by a REST client + service layer with
  friendly error dialogs (`UIDialog`).
- **Runtime theme control** — switch between **Aura**, **Lumo** and **Base** styles plus a
  light/dark color-scheme toggle. Defaults to **Aura dark** via
  `Page.addStyleSheet(Aura.STYLESHEET)` + `Page.setColorScheme(DARK)` on attach (the official
  Vaadin 25 runtime-theming pattern); logs under the `[THEME-CONTROL]` prefix.
- **Lucide icon gallery** (`/lucide-icons`) — browse all 2 000+ icons from the
  `io.github.dr0idbot:v-lucide-icons` add-on with font-size, stroke width,
  color, fill, line cap/join controls, substring filter and pagination. Tiles are styled purely
  with CSS (CSS grid) using Vaadin base-style tokens; icon names appear as tooltips only.

## Modules

```
mewspace/                      parent pom (no parent of its own)
├── mewspace-backend/              server side jar: RestClient layer (BaseApiClient, countries
│                                  client + models) + CountriesService and UIException
├── mewspace-views/                shared views (countries grid, theme control, icon gallery)
│                                  and components (UIDialog; the mewspace.components package)
└── mewspace-ui/                   the runnable Spring Boot app, port 8080
```

Dependency direction: `ui → views → backend`. Package-level layering is unchanged
(`apiclient` and `service` packages live in one jar; `components` package lives next to
`views`). See `RENDER.md` for Docker/Render deployment.

## Requirements

- JDK 25
- Maven 3.9+

## Build & run

```bash
mvn clean install                        # full build (includes the Vaadin production frontend)
mvn spring-boot:run -pl mewspace-ui      # run the app on http://localhost:8080
```

The run command works from the project root once the other modules are installed in the local
repository (`mvn install` first).

## Configuration

Properties live in `mewspace-ui/src/main/resources/application.properties`:
`server.port`, `vaadin.allowed-packages`, `logging.level.org.atmosphere=warn`,
`mewspace.api.client.*` (client timeouts), `mewspace.api.countries.base-url`
(default `https://countries.dev`).

## Project docs

- `AGENTS.md` — agent handbook (module layout, conventions, quirks)
- `references/REFERENCE.md` — Vaadin base style CSS custom properties (`--vaadin-*`)
