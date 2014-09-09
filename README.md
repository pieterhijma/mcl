Many-Core Levels
===

# Introduction

Many-Core Levels (MCL) is a framework that allows one to write computational
kernels for different kinds of many-core hardware. It consists of two
languages: a hardware description language HDL and a programming language MCPL. 
HDL allows one to define hardware with different levels of detail, which
results in different abstraction levels. MCL defines a hierarchy of hardware
descriptions, starting with hardware description *perfect*. Each lower-level
hardware description defines many-core hardware in more detail. MCPL allows one
to write computational kernels that are mapped to hardware by specifying which
hardware description is targeted and by using constructs that define a mapping
between algorithm and hardware. For more details, refer to [1]. 

# License

MCL is licensed under the Apache 2.0 license.

# Copyright

Copyright 2014 Pieter Hijma

# Contributers

- Pieter Hijma - pieter@cs.vu.nl 
- Ceriel Jacobs - ceriel@cs.vu.nl

Affiliation: VU University Amsterdam, Department of Computer Science.

# References

[1] P.Hijma, R.V. van Nieuwpoort, C.J.H. Jacobs, and H.E. Bal. Under Review.
    2014


