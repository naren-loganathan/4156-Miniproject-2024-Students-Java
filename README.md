### COMS 4156: Individual Assignment 1

**Naren Loganathan (nl2878)**

 - MacOS environment (IntelliJ IDEA 2022.1.1 CE)
 - java: 17.0.12-tem
 - maven: 3.9.5

---

 - `mvn spring-boot:run -D spring-boot.run.arguments="setup"` runs
 - `mvn checkstyle:check` yields no warnings/violations, `mvn checkstyle:checkstyle` report generated doesn't contain anything
 - `mvn test` runs tests
 - `mvn jacoco:report` generates `index.html` within `target/site/jacoco` (> 55% overall branch coverage)
 - Used PMD 7.5.0 as the static code analyzer of choice (followed the quick start commands on https://pmd.github.io/), used the `quickstart.xml` ruleset.
 - PMD command: `pmd check -d src -R rulesets/java/quickstart.xml -f text` (ran from `IndividualProject` dir)

---

 - Root of repo should also contain `bugs.txt`, `honesty.txt` and `citations.txt`
