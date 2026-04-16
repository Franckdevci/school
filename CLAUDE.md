# School Project — CLAUDE.md

## Stack technique
- Spring Boot 3.3.4 / Java 17
- MySQL 8 + Spring Data JPA / Hibernate
- Lombok (réduire le boilerplate)
- SpringDoc OpenAPI 2.1.0 (Swagger UI)
- JUnit 5 + Mockito (tests unitaires)
- Maven (build + déploiement Nexus)

## Commandes utiles
- Build : `mvn clean package`
- Run : `mvn spring-boot:run`
- Tests : `mvn test`
- Package sans tests : `mvn clean package -DskipTests`

## Architecture
```
StudentController  →  IStudentService  →  StudentServiceImpl  →  StudentRepository  →  MySQL
(control/)             (service/)          (service/)             (repository/)
```
- Toujours définir une **interface** de service avant l'implémentation
- Injection de dépendances **uniquement via constructeur** (`@AllArgsConstructor`)
- Jamais de `@Autowired` sur les champs

## Conventions de code
- Jamais de `.get()` direct sur un `Optional` — toujours `.orElseThrow(() -> new RuntimeException(...))`
- Utiliser `JpaRepository` (pas `CrudRepository`) pour éviter les casts dangereux
- `GenerationType.IDENTITY` pour les clés primaires MySQL (pas `AUTO`)
- Valider les entrées avec `@Valid` dans les controllers et les annotations Bean Validation sur les entités (`@NotBlank`, `@Min`, `@Max`, etc.)
- Ne jamais commiter de credentials en dur — utiliser `${ENV_VAR:valeur_defaut}` dans `application.properties`

## Gestion des erreurs
- Toujours utiliser un `@RestControllerAdvice` global pour intercepter les exceptions
- Retourner des réponses HTTP cohérentes (404 si entité introuvable, 400 si validation échoue)

## Tests
- Utiliser `Mockito.any(Classe.class)` — `anyObject()` est deprecated
- Tester tous les cas : succès, entité introuvable, données invalides
- Ne pas mocker la BDD pour les tests d'intégration

## Base de données
- BDD locale : `school_db` sur `localhost:3306`
- Credentials via variables d'environnement : `DB_USERNAME`, `DB_PASSWORD`
- `spring.jpa.hibernate.ddl-auto=update` en dev uniquement

## URLs locales
- API : http://localhost:8089/school
- Swagger UI : http://localhost:8089/school/swagger-ui/index.html

## CORS
- En développement : `@CrossOrigin(origins = "http://localhost:4200")`
- Ne jamais utiliser `origins="*"` en production

## Gitflow

### Branches
```
master        → production (stable, jamais de commit direct)
develop       → intégration continue
feature/xxx   → nouvelles fonctionnalités (depuis develop)
fix/xxx       → corrections de bugs (depuis develop)
release/x.x.x → préparation de release (depuis develop → merge dans master + develop)
```

### Workflow feature
```bash
git checkout -b feature/nom-feature develop
# ... développement ...
git checkout develop && git merge --no-ff feature/nom-feature
git branch -d feature/nom-feature
```

### Convention des messages de commit
Format : `type: description courte en français ou anglais`

| Type       | Usage                                      |
|------------|--------------------------------------------|
| `feat:`    | Nouvelle fonctionnalité                    |
| `fix:`     | Correction de bug                          |
| `refactor:`| Refactoring sans changement de comportement|
| `test:`    | Ajout ou modification de tests             |
| `chore:`   | Config, build, dépendances                 |
| `docs:`    | Documentation                              |

Exemples valides :
- `feat: add global exception handler`
- `fix: replace .get() with .orElseThrow() in updateStudent`
- `refactor: switch from CrudRepository to JpaRepository`
- `test: add unit tests for registerStudent`
- `chore: externalize DB credentials to environment variables`

### Règles
- Ne jamais commiter directement sur `master`
- Toujours merger avec `--no-ff` pour conserver l'historique des branches
- Tagger chaque release : `git tag -a v1.0.0 -m "Release 1.0.0"`
- Ne jamais utiliser `"Update"` comme message de commit

## Ce qu'il ne faut PAS faire
- Ne pas utiliser `(List<Student>) repository.findAll()` avec CrudRepository
- Ne pas appeler `.get()` sur un Optional sans vérification
- Ne pas commiter `root/root` ou tout autre credential dans `application.properties`
- Ne pas ignorer les erreurs de validation silencieusement
