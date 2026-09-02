# 🔴 I DON'T KNOW IF CORRECT!!

# ComplexTypeToStr Output Cheat Sheet

## Basic values

| Kotlin value  | Output    |
| ------------- | --------- |
| `null`        | `null`    |
| `"hello"`     | `"hello"` |
| `42`          | `42`      |
| `3.14`        | `3.14`    |
| `true`        | `true`    |
| `MyEnum.TEST` | `TEST`    |

## Lists

```text
listOf(1, 2, 3)
→ List<Integer>[1, 2, 3]

listOf("a", "b")
→ List<String>["a", "b"]

listOf(true, false)
→ List<Boolean>[true, false]
```

## Sets

```text
setOf(1, 2, 3)
→ Set[1, 2, 3]

setOf("a", "b")
→ Set["a", "b"]
```

## Maps

```text
mapOf("age" to 17, "name" to "Joe")
→ Map["age"=17, "name"="Joe"]
```

## Arrays

```text
arrayOf(1, 2, 3)
→ Array<Integer>[1, 2, 3]

arrayOf("a", "b")
→ Array<String>["a", "b"]

intArrayOf(1, 2, 3)
→ Array<Integer>[1, 2, 3]
```

## Nested values

```text
listOf(listOf(1, 2), listOf(3, 4))
→ List<ArrayList>[List<Integer>[1, 2], List<Integer>[3, 4]]
```

```text
mapOf(
    "numbers" to listOf(1, 2),
    "active" to true
)
→ Map["numbers"=List<Integer>[1, 2], "active"=true]
```

## Custom classes

Given:

```kotlin
data class Person(
    val name: String,
    val age: Int
)
```

```text
Person("Joe", 17)
→ Person(name="Joe", age=17)
```

Nested:

```kotlin
data class Address(
    val city: String
)

data class Person(
    val name: String,
    val age: Int,
    val address: Address
)
```

```text
Person("Joe", 17, Address("Berlin"))
→ Person(name="Joe", age=17, address=Address(city="Berlin"))
```

## VarInfoListToStr

The complete output format is:

```text
vars: { name:type:value, name:type:value }
```

Example:

```text
vars: { age:Int:17, name:String:"Joe", person:Person:Person(name="Joe", age=17) }
```

## Quick format reference

```text
null
String        → "value"
Number        → value
Boolean       → value
Enum          → ENUM_NAME

List          → List<Type>[values]
Set           → Set[values]
Map           → Map[key=value, key=value]
Array         → Array<Type>[values]

Custom class  → ClassName(field=value, field=value)
```

## Important current behavior

* `Set` does not include its element type.
* `Map` does not include key/value types.
* `List` includes the first element's class name as its type.
* `Array` includes the first element's class name as its type.
* Empty `List`/`Array` uses `?` as the type.
* Custom classes only serialize fields that:

  * are declared in the class
  * are not synthetic
  * are not static
  * exist in the primary constructor
* Fields are output in primary-constructor order.
