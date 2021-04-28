# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [1.1.0-SNAPSHOT]


## [1.0.0] - 2021-02-02

Breaks module names!
Breaks persistence API!

### Added

- In data module:
    - Order
    - Version
    - Entity
    - EntityVersioned
    - Repository
    - RepositoryVersioned
    - JdbcTemplate (with Micronaut and Spring implementations)
        
- In tx module
    - Support of Micronaut transaction manager (now you can choose use Micronaut -recommended-, or Spring) 

### Changed

- Rename module `arch-persistence` to `arch-data-tx` 
- Rename module `arch-persistence-query` to `arch-data`
- Rename all module names from `arch-xxx` to `archimedes-xxx` 
- Package `io.archimedesfw.persistence` to `io.archimedesfw.data`
- API of `Finder`, `QueryBuilder` and other data classes
- Rename database elements to be snake_case, like table "AuditLog" and its columns "elapsedTime", "userId", "useCase" and "readOnly"

### Removed

- Micronaut request filter to set security context is no more needed
  

## [0.0.1-SNAPSHOT] - 2020-05-12

First version

### Added

- Commons lang
- Commons logging slf4j
- Bulk action
- Persistence
- Persistence query
- Service Locator
- Security
- Use case
- BOM
