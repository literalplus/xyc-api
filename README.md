XYC-API
========

About
------

This project provides some interfaces and simple classes which are commonly used in my projects.
Some parts contain a complete implementation, while for others, only the API is
provided. These APIs may be implemented by other projects, which may or may not be free software.

Generally, XYC-API is intended to be used with [the Spigot Minecraft server](https://spigotmc.org),
although it might be applicable for other usages too.

Compilation
-----------

While it does not make much sense to have a library compiled alone, it does make sense to deploy
it to your local Maven repository so that your local projects may depend on it. XYC-API uses Maven
for compilation:

````bash
mvn clean install
````

Dependency information
-----------------------

For our convenience, this project is also deployed to my Maven repository. You need the following
repository declaration in your pom file:

````xml
<repository>
  <id>xxyy-repo</id>
  <url>https://repo.l1t.li/xxyy-public/</url>
</repository>
````

To add a dependency on this project, add the following to the `<dependencies>` section of your pom:

````xml
<dependency>
  <groupId>li.l1t.common</groupId>
  <artifactId>xyc-api</artifactId>
  <version>${xyc-api.version}</version>
</dependency>
````

You can look up the latest release version [at my build server](https://ci.l1t.li/job/public~xyc-api).

Versioning
----------

Contrary to the implementation of XYC, which uses four version numbers, XYC-API only uses three.

````
4.0.1
^ ^ ^
| | |_ API release release: Non-breaking API changes and minor additions, independent of XYC
| |
| |_ XYC major release: Indicates which XYC version this version is compatible with
|
|_ XYC overall release: Indicates the XYC overall version this version is compatible with
````

License
-------

This project is licensed under the MIT license:

````
MIT License

Copyright (c) 2016 Philipp Nowak (Literallie)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
````
