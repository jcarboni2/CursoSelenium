# CursoSelenium 🧪

Estudos com Selenium WebDriver baseados no curso do Wagner Aquino.

## Pré-requisitos
- JDK 17, Maven 3.9+, Chrome atual, IDE em UTF-8
- Selenium 4.49 resolve o driver sozinho (sem baixar chromedriver)

## Rodando
```bash
git clone https://github.com/jcarboni2/CursoSelenium.git
cd CursoSelenium

mvn clean test              # headless (padrão: rápido, funciona em CI)
mvn test -Dheadless=false   # com o browser visível (debug local)
mvn test -Dtest=CadastroTest    # um teste só
mvn test -Dtest=SuiteTest       # suíte (Cadastro + Regras)
mvn package -DskipTests         # pular testes
```

Pela IDE: importe o `pom.xml` (JDK 17) e dê Run em `SuiteTest` ou qualquer `*Test` (roda headless por padrão; para ver o browser, adicione a VM option `-Dheadless=false`).

## Como funciona
- **Testes no build:** layout padrão `src/test/java` com nomes `*Test` — o Surefire detecta tudo sem config extra.
- **Browser único:** o Chrome abre uma vez e é reaproveitado por todos os testes (fecha na suíte ou no fim da JVM).
- **Página por classe:** cada classe carrega sua página uma vez (`@BeforeClass` → `abrirPagina`) e dá só `refresh` antes de cada teste (`recarregarPagina`) — bem mais rápido.
- **Prints:** `target/screenshot/<Teste>.jpg` | **Relatórios:** `target/surefire-reports/`

## Grid (opcional)
```bash
docker compose up -d
mvn test -Dselenium.grid.url=http://localhost:4444/wd/hub
docker compose down
```

## Se der erro
- `0 testes` → rode `mvn clean test` (já configurado no pom).
- `localhost:4444` sem resposta → rode sem flags (driver local) ou suba o compose.
- Erro de Docker/Testcontainers → padrão é local; só use `-Dselenium.grid.container=true` com Docker atual.
- Linux sem Chrome instalado (ex.: container Alpine) → `mvn test -Dwebdriver.chrome.driver=/usr/bin/chromedriver`.
