# PublishingPlugins

The plugins in this repository are designed to help you publish Java or Android libraries and assist in finding the packaged APK or AAR files, while minimizing the modifications required to your project.

Here are the plugins included in this repository and their descriptions:

## `android-publish`/`java-publish`

This plugin is used to publish Android and Java libraries to Maven Central and is primarily suited for use cases where a single repository is used to publish multiple interrelated libraries.

### Setup

```kotlin
plugins {
    id("maven-publish") // required
    id("signing") // required
    
    id("io.github.sgpublic.android-publish") version "0.1.0"
    id("io.github.sgpublic.java-publish") version "0.1.0"
}
```

### Configuration

Once the plugin is added, you will need to add the following fields to either the `gradle.properties` file in your project or the `$HOME/.gradle/gradle.properties` file:

+ Nexus Repository Manager Auth

  ```properties
  publising.username=xxx
  publising.password=xxx
  ```

  [Nexus Repository Manager](https://oss.sonatype.org/) login username and password are required for the plugin to function, and must be added after these fields are added.

+ Signing

  ```properties
  signing.keyId=xxx
  signing.password=xxx
  signing.secretKeyRingFile=xxx
  ```

  These parameters are required by the `signing` plugin. For more information, please refer to the [official documentation](https://docs.gradle.org/current/userguide/signing_plugin.html).

+ Publishing

  ```properties
  publising.project.group=com.example
  publising.project.version=1.0.0-example
  publising.project.name=example # (Optional) Project name. If not set, the rootProject.name value in Gradle will be used.
  publising.project.url=https://github.com/example/project
  publising.developer.id=xxx
  publising.developer.name=xxx
  publising.developer.email=publish@example.com
  publising.license.name=The Apache License, Version 2.0
  publising.license.url=http://www.apache.org/licenses/LICENSE-2.0.txt
  publising.issue.system=GitHub
  publising.issue.url=https://github.com/example/project/issues
  publising.smc.connection=scm:git:git://github.com/example/project.git
  publising.smc.developerConnection=scm:git:git@github.com:example/project.git
  publising.smc.url=https://github.com/example/project
  ```

  These parameters will be used to configure the `maven-publish` plugin. For details on how to configure the plugin, please refer to the [official documentation](https://docs.gradle.org/current/userguide/publishing_maven.html).

### Usage

Assuming a gradle project structure as follows:

```
library-project
├─library-a
└─library-b
```

And `publishing.project.name` is set to `example`:

```properties
publising.project.name=example
```

Next, the plugin will add the following Gradle tasks for your project:

+ publishExampleLibraryAPublicationToOssrhRepository
+ publishExampleLibraryBPublicationToOssrhRepository

You can use these tasks to publish the corresponding libraries.

Alternatively, you can use `publishAllPublicationsToOssrhRepository` to publish all libraries with a single command.
