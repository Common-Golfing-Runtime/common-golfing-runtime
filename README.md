# Common Golfing Runtime

A virtual machine for code golfing languages. Loosely based on the JVM.

## How to run

For now, there is no release of CGR, so to compile it execute `./gradlew shadowJar`.

To invoke the virtual machine, run the following command:
```shell
java -jar cgr.jar <file> [options]
```
`<file>` is the input file. `[options]` can be the following:

| Short form | Long form | Description |
| --- | --- | --- |
| `-a <out>` | `--assemble <out>` | Instead of running `<file>`, assemble `<file>`, outputting into `<out>` |
