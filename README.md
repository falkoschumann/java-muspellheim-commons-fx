[![Download](https://api.bintray.com/packages/falkoschumann/maven/muspellheim-commons-fx/images/download.svg)](https://bintray.com/falkoschumann/maven/muspellheim-commons-fx)
[![Build Status](https://travis-ci.org/falkoschumann/java-muspellheim-commons-fx.png?branch=master)](https://travis-ci.org/falkoschumann/java-muspellheim-commons-fx)
[![Sonar Coverage](https://img.shields.io/sonar/coverage/falkoschumann_java-muspellheim-commons-fx?server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=falkoschumann_java-muspellheim-commons-fx)
[![Sonar Quality Gate](https://img.shields.io/sonar/quality_gate/falkoschumann_java-muspellheim-commons-fx?server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=falkoschumann_java-muspellheim-commons-fx)

# Muspellheim Commons FX

Bundles common classes for developing Java apps with JavaFX.

## Installation

Add the dependency to your build.

### Gradle

Add JCenter repository:

    repositories {
        jcenter()
    }

Add dependency:

    implementation 'de.muspellheim:muspellheim-commons-fx:1.0.0'

### Maven

Add JCenter repository:

    <repositories>
        <repository>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <id>jcenter</id>
            <name>jcenter</name>
            <url>http://jcenter.bintray.com</url>
        </repository>
    </repositories>

Add dependency:

    <dependency>
        <groupId>de.muspellheim</groupId>
        <artifactId>muspellheim-commons-fx</artifactId>
        <version>1.0.0</version>
    </dependency>

## Usage

See [JavaDoc](https://falkoschumann.github.io/java-muspellheim-commons-fx/).
