# Big Numbers Handler

[![GitHub license](https://img.shields.io/github/license/AlexandreLadriere/Big-Numbers-Handler.svg)](https://github.com/AlexandreLadriere/Big-Numbers-Handler/blob/master/LICENSE)

This repository is the result of a lab I had during my formation in _Mobility & Security_ at the EMSE.
This project aims to handle big numbers for cryptographic purposes.

__Disclaimer:__ This project had to be done in a certain amount of time. So the code is not perfect and is not specially optimized. I am aware that there are simpler methods for this kind of problem. 

## Description
The goal of this project was to implement a class able to handle calculus with big numbers (more than 64 bits, and especially 256-bit long numbers).
In order to do that, the [BigInt.java] class represents numbers with a list of (signed) integers. Since integers on are coded 32 bits, the integer list has a size of ```bit_length / 32```, and the maximum for each integer is ```2³¹ = 2147483647``` since they are signed and coded on 32 bits.

For example, ```18014398509482038``` will be represented by ```[0, 0, 0, 0, 0, 0, 8388608, 54]```
 
By default a ```BigInt``` object has a size of ```256 bits```. However, you can specify another size if you want (such as ```512 bits``` or ```1024 bits```). 

This class handles the following operations:
- Size comparison
- Equality comparison
- Classical addition
- Modular addition
- Classical subtraction
- Modular subtraction
- Classical multiplication
- _Modular multiplication (coming soon)_

## Tests
### Java tests
The [Test.java] file runs each operation with a pre-determined A, B and P (the modulus). You can change these parameters values in the ```iniVariables``` function.

For example, by running this file, you will get the following output:
```bash
A = BigInt[0, 0, 0, 0, 0, 700, 2147483647, 2147483647]
B = BigInt[0, 0, 0, 0, 0, 0, 1309, 2147483647]
P = BigInt[0, 0, 0, 0, 2, 0, 0, 0]
A.isGreater(B) = true
A.isEqual(B) = false
A.add_mod(B, P) = BigInt[0, 0, 0, 0, 0, 701, 1309, 2147483646]
A.sub_mod(B, P) = BigInt[0, 0, 0, 0, 0, 700, 2147482338, 0]
A.mul(B) = BigInt[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 918309, 2147482946, 2147482338, 1]
A.mul(B).mul(A) = BigInt[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 643735309, 2146992246, 2145647028, 1402, 1309, 2147483647]
A.mul(B).mul(A.mul(B)) = BigInt[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 392, 1479666083, 860013026, 1889486497, 3673240, 1714697, 2147481028, 1]
```
### Python tests
The [test.py] file was created for development purpose. It runs the same calculus, but with using python large numbers implementation. This test file was used to verify the different results returns by the [Test.java]
You can also change values for A, B and P in this file.  
For example, by running this file, you will get the following output:
```bash
A = BigInt[0, 0, 0, 0, 0, 700, 2147483647, 2147483647]
B = BigInt[0, 0, 0, 0, 0, 0, 1309, 2147483647]
P = BigInt[0, 0, 0, 0, 2, 0, 0, 0]
A.isGreater(B) = true
A.isEqual(B) = false
A.add_mod(B, P) = BigInt[0, 0, 0, 0, 0, 701, 1309, 2147483646]
A.sub_mod(B, P) = BigInt[0, 0, 0, 0, 0, 700, 2147482338, 0]
A.mul(B) = BigInt[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 918309, 2147482946, 2147482338, 1]
A.mul(B).mul(A) = BigInt[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 643735309, 2146992246, 2145647028, 1402, 1309, 2147483647]
A.mul(B).mul(A.mul(B)) = BigInt[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 392, 1479666083, 860013026, 1889486497, 3673240, 1714697, 2147481028, 1]
```

## License
This project is licensed under the MIT License - see the [LICENSE] file for details.

  [LICENSE]: <LICENSE>
  [BigInt.java]: <src/BigInt.java>
  [Test.java]: <src/Test.java>
  [test.py]: <test.py>