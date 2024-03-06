package analytikt.operators

import analytikt.base.Operator

/**
 * One type of operator is called a Relation.
 * This is an operator whose result domain is the boolean domain. While traditionally in mathematics relations are not instances of Operators but a separate structure of their own, the abstractions we provide give enough flexibility for the inheritance to be well-defined. Examples of relations are:
 * A Term of a domain being in a set of the domain
 * A number being greater than another number
 * A Point being in a shape
 * 3 points being on the same line
 */
abstract class Relation<TArgDomain> : Operator<TArgDomain, Boolean>()