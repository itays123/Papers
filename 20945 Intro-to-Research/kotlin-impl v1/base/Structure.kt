package analytikt.base

/**
 * A mathematical Structure is an abstraction of all mathematical objects.
 * It is a generic class with the generic parameter TDomain that corresponds to the domain set on which the structure is defined.
 * Many structures can be domain sets themselves: For example, The set of real number sets, the set of matrices above the real
 * numbers and the set of polynomials above the real numbers are all structures of real numbers and also domain sets themselves,
 * and lines and polygons are structures of Points inside a 2d or even 3d space.
 */
interface Structure<TDomain>

