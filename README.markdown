# Fast & Furious with GraalVM

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![GitHub last commit](https://img.shields.io/github/last-commit/CarloMicieli/fast-and-furious-with-graalvm)


[![Micronaut CI](https://github.com/CarloMicieli/fast-and-furious-with-graalvm/actions/workflows/micronaut-ci.yaml/badge.svg)](https://github.com/CarloMicieli/fast-and-furious-with-graalvm/actions/workflows/micronaut-ci.yaml)
[![Spring Boot CI](https://github.com/CarloMicieli/fast-and-furious-with-graalvm/actions/workflows/spring-boot-ci.yaml/badge.svg)](https://github.com/CarloMicieli/fast-and-furious-with-graalvm/actions/workflows/spring-boot-ci.yaml)

A playground repository to experiment with the GraalVM native compiler.

## Requirements

* Java 17 
* Git
* Docker

## How to run

```bash
  git clone https://github.com/CarloMicieli/fast-and-furious-with-graalvm.git
```

```bash
  sdk install java 22.3.r17-grl
  sdk use java 22.3.r17-grl
```

## Running a PostgreSQL Database

Run PostgreSQL as a Docker container

```bash
docker run  -it --rm --name games-db-postgres \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=mysecretpassword \
    -e POSTGRES_DB=gamesdb \
    -p 5432:5432 \
    postgres:15.1-alpine
```

### Micronaut

```bash
  ./gradlew micronaut-app:run
```

the server is listening at http://localhost:8002

### Spring boot 

```bash
  ./gradlew spring-boot-app:bootRun
```

the server is listening at http://localhost:8000

## Contributing

Contributions are always welcome!

See `CONTRIBUTING.markdown` for ways to get started.

Please adhere to this project's `code of conduct`.

### Conventional commits

This repository is following the conventional commits practice.

#### Enforcing using git hooks

```bash
  chmod +x .githooks/commit-msg
  git config core.hooksPath .githooks
```

The hook itself can be found in `.githooks/commit-msg`.

#### Using Commitizen

Install [commitizen](https://github.com/commitizen-tools/commitizen)

```bash
  pip install commitizen
```

and then just use it

```bash
  cz commit
```

## License

[Apache 2.0 License](https://choosealicense.com/licenses/apache-2.0/)

```
   Copyright 2023 Carlo Micieli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
