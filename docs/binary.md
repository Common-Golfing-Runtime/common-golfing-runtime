# The Binary Structure of a Compiled File

For simplicity, all mentions of `int` and `string` in this file are specified to be [MessagePack](https://msgpack.org) types. The binary specification for each MessagePack type can be found [here](https://github.com/msgpack/msgpack/blob/master/spec.md). An array of a type is specified by a `[]` suffix. An optional value is specified by a `?` suffix.

## The File Specification

The file always starts with the magic number `0xDEADC0DE`.

```
symbol_table_length: int
symbol_table: string[]

constant_table_length: int
constant_table

opcode_table_length: int
opcode_table
```

All extra data at the end of the file is ignored.

### The Symbol Table

The symbol table is an array of strings. As the virtual machine is highly extendable, the opcode and constant label definitions are not fixed. Instead, the symbol table contains a list of all the names of the opcodes and constant labels used. The constant table and opcode table contain indexes into the symbol table.

### The Constant Table

The constant table lists all of the compile time constants used in the program. The constant table is an array of constant definitions. Each constant definition is a tuple of the following form:

```
constant_type: int
constant_value: any
```

The `constant_type` is an index into the symbol table. The `constant_value` is the value of the constant. It is parsed by the type registered to the `constant_type` in the virtual machine.

### The Opcode Table

The opcode table is an array of opcodes. Each opcode is a tuple of the following form:

```
opcode_name: int
opcode_args_length: int?
opcode_args: int[]
```

The `opcode_name` is an index into the symbol table. The length of the `opcode_args` array is specified by the opcode specification. If the length in the specification is specified to be exactly -1, then the length of the `opcode_args` array is specified by the `opcode_args_length` field.