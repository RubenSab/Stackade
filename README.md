# 1. Introduction and usage

Stackade is a high-level, stack based, interpreted language. It features a tiny core consisting of a few primitives and built in operators coupled with a **stack** and a **namespace**.

## 1.1. Stack and Namespaces

The **Stack** and **Namespaces** are central to Stackade:

- the **Stack** is a data structure that only supports knowing the top element (peeking), removing it (popping) and adding a new element on top (pushing). Almost everything in Stackade interacts with it: operators and sequences *(read 2.1.6.)* take **arguments** by popping elements and push the **results** to return them.
  
  For example, the operator `+` takes the pops the lasts two element from the stack and pushes their sum.

- the **Namespaces** are a linked list of maps, each linking the variable name to it's content. Having multiple namespaces in a hierarchy allows **local scoping** and cleansing un-needed local variables after a **sequence** is executed.

> Note: Variables names (so the names of everything stored in namespaces) allow every printable Unicode character, besides `#`, rounded brackets and curly brackets.


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
"box" 1 :box
```

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

For ease of description, when appliable, arguments (pops) of operators are written as:

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
	- pops: `A(ref)`
	- deletes the variable `A`

- `:raise`
	- pops: `A(ref)`
	- deletes the variable `A` from the local namespace and adds it to the caller's one, eventually replacing the old definition

##### Definitions

They define a variable of name `B` and value `A` of the specified (immutable) type. 

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
	- pops: `A(str)` the source file to run inside the current program, without creating a new namespace (this allows programs to have dependencies to other programs used to reuse functions)

---
# 3. Flow control

Stackade has a single flow control structure called **Conditional Block**.

Its syntax is as follows (but it can be indented on multiple lines to enhance clarity)

```
{(conditionSequence)(trueSequence)(falseSequence)}
```

The interpreter executes `conditionSequence`; if the top of the stack is `true`, it executes the `trueSequence`, otherwise it executes the `falseSequence`.

## 3.1. The `self` keyword

The interpreter is always aware of the current block in execution, so the cycle can reference itself from one of its branches using `self` as a simple syntactic substitution.

For example, this program counts from 0 to 10:

```
{
    (dup 10 <=)
    (dup print " " print 1 + self) # true
    ("done!" print)                  # false
}
```