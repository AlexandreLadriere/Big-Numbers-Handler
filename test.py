def intToString(a, bit = 256):
    """
    Converts an integer to a binary string representation (without '0b')
    :param a: The integer to convert (integer)
    :param bit: The size of the representation in bit (integer)
    :return: Binary string representation (String)
    """
    str = bin(a)[2:]
    return (bit-len(str))*"0" + str

def bitStringtoSigned(str, block_size = 32):
    """
    Inserts each block_size step a zero in order to represent
    a string of block_size signed integer in binary format from the 
    original string (loss of information)
    :param str: Original string of an integer representation in binary format (String)
    :param block_size: step for the insertion of 0 (Integer)
    :return: string of block_size signed integer in binary format (String)
    """
    signed_str = ""
    cpt = 0
    reverse_str = str[::-1]
    for i in range(0, len(str), block_size-1):
        cpt += 1
        print("reverse_block =", reverse_str[i:i+block_size-1] + '0')
        signed_str += reverse_str[i:i+block_size-1] + '0'
    signed_str = signed_str[::-1]
    signed_str = signed_str[cpt:]
    print("signed_str =", signed_str)
    return signed_str

def bitStringToIntArray(str, block_size = 32):
    """
    Converts a string of a represention in binary format of
    an integer into an array of integer of block_size-1 bit-size
    :param str: Original string to convert into an array of integer (String)
    :param block_size: Size of blocks (Integer)
    :return: Representation of an integer array of the given string representation
    """
    int_array = []
    signed_str = bitStringtoSigned(str, block_size)
    for i in range(0, len(signed_str), block_size):
        print("block =", signed_str[i:i+block_size])
        tmpStr = signed_str[i:i+block_size]
        print(len(tmpStr))
        int_array.append(int(tmpStr, 2))
    return int_array

def convertBigIntToIntArray(big_int, bit = 256, block_size = 32):
    """
    Converts a big integer into an array representation. Each array is coded
    on 2^:block_size:-1 bits and the representation has a total length of :bit: bits
    :param big_int: The integer you want to convert (integer)
    :param bit: The length in bits of the representation (integer)
    :param block_size: The initial size of each integer in the final array in bits (integer)
    :return: An array representation of the integer
    """
    str = intToString(big_int, bit)
    int_array = bitStringToIntArray(str)
    return int_array

if __name__ == "__main__":
    str_test = intToString(2147483647*2)
    print(str_test)
    int_array_test = bitStringToIntArray(str_test)
    print(int_array_test)