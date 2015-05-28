BOL is language to create external DSLs quickly and efficiently.
There are two approaches to using domain specific languages in a system - internal and external.

External DSLs have a vocabulary that matches more closely with the business language and can easily be used by the business analyst types who can handle a bit of programming.

However, creating an external DSL is not a straight forward process and requires specialized tools and resources.  BOL is an effort to minimize and standardize the process of creation of external DSLs.


Some of the key features proposed are:

  1. Support for closures and higher order functions
  1. Framework for maintaining a repository of DSLs
  1. Support for multiple target languages and runtime, starting with Java/JVM.


Current Status:
Parsing of source and creation of abstract syntax tree is complete.  The next items in order of priority are:

  1. Code generation for Java
  1. Byte code generation
  1. Framework for repository maintenance/lookups


Sounds interesting?  Check out the wiki page.
_Interested to join the development_?  Let me know.