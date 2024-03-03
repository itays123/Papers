package analytikt.base

/**
 * Represents a term that cannot be split further
 */
abstract class AtomicTerm<TDomain>(domain: DomainDescriptor<TDomain>) : Term<TDomain>(domain)