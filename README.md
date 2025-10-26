# Installation and usage of the interpreter

Download the desired jar file from Releases (on the right pane), then you'll be able to run the interpreter from the command line using this command:

```
java -jar stackade.jar <program name>
```

If you are on Linux, you can run it with a single command from every directory following these steps:

1. Go to Releases and download the desired jar file.
2. Create a file named just `stackade` in the same directory.

Write this script inside the new file `stackade`:

```bash
#!/bin/sh
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
java -jar "$SCRIPT_DIR/stackade.jar" "$@"
```

3. Run this to move `stackade` to a directory in PATH:

```
sudo mv stackade /usr/local/bin/
```

4. Make it executable:

```
chmod +x /usr/local/bin/stackade
```

5. Now every user in the system can run the interpreter with:

```
stackade <program name>
```

---

# Language Documentation

# 1. Introduction

Stackade is a high-level, stack based, concatenative interpreted language. It features a tiny core consisting of a few primitives and built in operators coupled with a **stack** and a **namespace**.

## 1.1. Stack and Namespaces

The **Stack** and **Namespaces** are central to Stackade:

- the **Stack** is a data structure that only supports knowing the top element (peeking), removing it (popping) and adding a new element on top (pushing). Almost everything in Stackade interacts with it: operators and sequences *(read 2.1.6.)* take **arguments** by popping elements and pushing the **results** to return them.
  
  For example, the operator `+` pops the lasts two elements from the stack and pushes their sum.

> IMPORTANT: almost every language element is a stack operator, or it's made from stack operators, as *even data types* execute by pushing themselves to the stack.

- the **Namespaces** are a linked list of maps, each linking the variable name to its content. Having multiple namespaces in a hierarchy allows cleansing un-needed local variables after a **sequence** is executed.

> Note: Variables names (so the names of everything stored in namespaces) allow every printable Unicode character, besides `#`, rounded brackets and curly brackets.

## 1.2. Naming

- variables are named following snakeCase;
- comments are preceded by '#';
- lines are completely irrelevant for the interpreter, but they can be broken down arbitrarily and commented to enhance legibility;
- Branches of Conditional Blocks (read 3.) should be indented if they consist of a sufficient number of elements.

---
# 2. Data types and operators

## 2.1. Data types

### Primitives

#### 2.1.1. Number

**Literals**: the literals of Number are the representation of said number, with a dot to separate the integer part from decimals.
**Type identifier**: `num`
**Definition example**: `"myNum" 3 :num`

#### 2.1.2. String

**Literals**: the literals of String are the representation of said string, surrounded by double quotes.
**Type identifier**: `str`
**Definition example**: `"myStr" "hello" :str`

#### 2.1.3. Boolean

**Literals**: the literals of Boolean are `true` and `false` according to their value.
**Type identifier**: `bool`
**Definition example**: `"myBool" true :bool`

### Other

#### 2.1.4. Reference

References are variables that refer to other variables.

**Literals**: the literals of Reference are the name of the variable they reference to.
**Type identifier**: `ref`
**Definition example**:

```
"n" 1 :num
"myReference" n :ref
```

#### 2.1.5. Box

Boxes can contain one element of any data type and their content can be changed freely. This allows the programmer to write a single sequence generalized for every data type.

**Literals**: the literals of Boxes are the literals of the variables they contain.
**Type identifier**: `box`
**Definition example**:

```
"box" 1 box :box
```

> Note: a variable needs to be *boxed (read 2.2.7.)* before being defined in the local namespace as a box

#### 2.1.6. Sequence

Sequences are blocks of code that isn't directly executed when processed, but is pushed to the stack as a single entity of type sequence. They are both used in Conditional Blocks *(read 3.)* and defined to be called later as **functions**.

**Literals**: the literals of Sequences are the code they consist of, surrounded by rounded brackets.
**Type identifier**: seq
**Definition example**:

```
"avgOfTwo" (+ 2 /) :seq

3 5 avgOfTwo print

[output: 4]
```

---
## 2.2. Operations

For ease of description, when applicable, arguments (pops) of operators are written as:

- `A(type)`: the element on top of the stack (of assumed type on the right) before operator's execution.
- `B(type)`: the element below A (of assumed type on the right) before operator's execution.
- `C(type)`: the element below B (of assumed type on the right) before operator's execution.

Pushes are written chronologically.

If the operation is type agnostic, then it's not specified or written as `any`.

If the operator accepts a reference to a variable of the type of its main argument type, then it's written `(type/ref)`.

### 2.2.1. Stack operations (type agnostic)

- `dup`
	- pops: `A`
	- pushes: `A`, `A`

- `pop`
	- pops: `A`

- `swap`
	- pops: `A`, `B`
	- pushes: `A`, `B`

- `rot`
	- pops: `A`, `B`, `C`
	- pushes: `A`, `B`, `C`

- `==`
	- pops: `A`, `B`
	- pushes: `(bool)` `true` if `A` equals `B` else `false`

- `!=`
	- pops: `A`, `B`
	- pushes: `(bool)` false if `A` equals `B` else `true`

- `stackHeight`
    - pushes: the number of elements inside the Stack.

### 2.2.2. Numeric args operations

#### With Number return

All fit the description:
- pops: `B(num/ref)`, `A(num/ref)`
- pushes: `(num)` the result of `B` operator `A`

- `+` addition
- `-` subtraction
- `*` multiplication
- `/` division
- `%` modulo
#### With Boolean return

All fit the description:
- pops: `B(num/ref)`, `A(num/ref)`
- pushes: `(bool)` the result of `B` operator `A`

- `<` less than
- `>` greater than
- `<=` less than or equal
- `>=` greater than or equal

### 2.2.3. Boolean args operations

All fit the description:
- pops: `B(bool/ref)`, `A(bool/ref)`
- pushes: `(bool)` the result of `B` operator `A`

- `not`
- `and`
- `or`
- `xor`

### 2.2.4. String operations

- `strAt`
	- pops: `B(str/ref), A(num/ref)`
	- pushes: `(str)` the character at position `A` in `B`

- `strCat`
	- pops: `B(str/ref), A(str/ref)`
	- pushes: `(str)` the concatenation of `B` and `A`

- `strLen`
	- pops: `A(str/ref)`
	- pushes: `(num)` the length of `A`

### 2.2.5 Reference Operations

- `refName`
	- pops `A(ref)`
	- pushes `(str)` `A` name.

- `refGet`
	- pops: `A(ref)`
	- pushes: `(any)` the content of the variable `A

### 2.2.6. Namespaces operations

- `exists`
	- pops: `A(ref)`
	- pushes: `(bool)` `true` if the variable named `A` exists else `false`


#### Mutations in Namespaces

- `=`
	- pops: `B(ref)`, `A`
	- assigns the variable `B` to `A`

- `:del`
	- pops: `A(str)`
	- deletes the variable named `A`

- `:raise`
	- pops: `A(str)`
	- deletes the variable named `A` from the local namespace and adds it to the caller's one, eventually replacing the old definition


##### Definitions

They define a variable of name `B` and value `A` of the specified (immutable) type. 

> Note: variables can be redefined as values of the same type, as if they were assigned to a different value. This helps in writing sequences that can't know if the variable was already assigned.

- `:num`
	- pops: `B(str)`, `A(num/ref)`

- `:str`
	- pops: `B(str), A(str/ref)`

- `:bool`
	- pops: `B(str), A(bool/ref)`

- `:ref`
	- pops: `B(str), A(ref)`

- `:box`
	- pops: `B(str), A(box/ref)`

- `:seq`
	- pops: `B(str), A(seq)`

### 2.2.7. Casting

- `type`
	- pops: `A`
	- pushes: `(str)` the type identifier of `A`

- `box`
	- pops: `A`
	- pushes: `(box)` a box containing `A`

- `unbox`
	- pops: `A(box)`
	- pushes: `(?)` `A` content

- `strToNum`
	- pops: `A(str/ref)`
	- pushes: `(num)` `A` converted to Number

- `strToRef`
	- pops: `A(str/ref)`
	- pushes: `(ref)` Reference to the variable named `A` (possibly an undefined one)

- `numToStr`
	- pops: `A(num/ref)`
	- pushes: `(str)` `A` converted to String

### 2.2.8. Self referencing

- `self`: executes again the Conditional Block in which is contained

### 2.2.9. I/O

- `print`
	- pops: `A(str/ref)`
	- prints `A` to console

- `input`
	- takes the input from console
	- pushes: `(str)` the user inputted string

### 2.2.10. Debugging

- `debug`
	- prints Stack and Namespaces state

- `halt`
	- halts the program

### 2.2.11. Source files inclusion

- `run`
	- pops: `(str)` the source file to run inside the current program, without creating a new namespace (this allows programs to have dependencies to other programs used to reuse functions)

### 2.2.12. Time

- `nanos`
  - pushes: `(num)` the current time in nanoseconds from an arbitrary point; only appropriate to measure elapsed time.

### 2.2.13 Higher order sequences

- `runSeq`
  - pops: `(seq)`
  - pushes: `(?)` the output (if any) of the popped sequence
---

# 3. Flow control

Stackade has a single flow control structure called **Conditional Block**.

Its syntax is as follows (but it can be indented on multiple lines to enhance clarity)

```
{(conditionSequence)(trueSequence)(falseSequence)}
```

The interpreter executes `conditionSequence`; if the top of the stack is `true`, it executes the `trueSequence`, otherwise it executes the `falseSequence`.

## 3.1. Interpreter directives

Some tokens don't directly trigger actions on the Stack or Namespace, but they determine how other tokens are executed and manipulate the execution flow of the program.

- `self` makes the current conditional block execute itself again.
- `break` immediately terminates the execution of the current conditional block. It then searches for the nearest enclosing "loop" block (one which contain the "self" directive) in the hierarchy and resumes execution from the block after it.
- `:seq` defines a sequence. It is also a directive because the interpreter doesn't execute a sequence if there's `:seq` right after it (read 2.2.6)

> Note: an _"anonymous sequence"_ is a sequence which is not defined in the namespace. Its body is written in the code without nor a name nor `:seq`.

- `@` pushes the anonymous sequence it follows.
- `!` executes the anonymous sequence it follows (useful in rare circumstances: normally, sequences are executed unless the interpreter is told otherwise).

Example: (using `compose` from "higherOrder" module)

```
(+ 2 *)@ (1 -)@ compose print
```

### 3.1.2 The `self` directive

The interpreter is always aware of the current block in execution, so the cycle can reference itself from one of its branches using `self` as a simple syntactic substitution.

For example, this program counts from 0 to 10:

```
0
{
    (dup 10 <=)
    (dup print " " print 1 + self)  # true
    ("done!" print)                 # false
}
```