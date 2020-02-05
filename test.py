def intToBitString(a, bit = 256):
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
    Inserts each :block_size: step a zero in order to represent
    a string of :block_size: signed integer in binary format from the 
    original string (loss of information)
    :param str: Original string of the integer in binary format (String)
    :param block_size: step for the insertion of 0 (Integer)
    :return: string of block_size signed integer in binary format (String)
    """
    signed_str = ""
    cpt = 0
    reverse_str = str[::-1]
    for i in range(0, len(str), block_size-1):
        cpt += 1
        signed_str += reverse_str[i:i+block_size-1] + '0'
    signed_str = signed_str[::-1]
    signed_str = signed_str[cpt:]
    return signed_str

def bitStringToIntArray(str, block_size = 32):
    """
    Converts a string of an integer in binary format (:block_size:bits signed, e.g. "0111 0010")
    into an array of integer of block_size-1 bit-size
    :param str: Original string to convert into an array of integer (String)
    :param block_size: Size of blocks (Integer)
    :return: Representation of an integer array of the given string representation (Integer array)
    """
    int_array = []
    signed_str = bitStringtoSigned(str, block_size)
    for i in range(0, len(signed_str), block_size):
        tmpStr = signed_str[i:i+block_size]
        int_array.append(int(tmpStr, 2))
    return int_array

def convertBigIntToIntArray(big_int, bit = 256, block_size = 32):
    """
    Converts a big integer into an array representation. Each array is coded
    on 2^:block_size:-1 bits and the representation has a total length of :bit: bits
    e.g. [0, 0, 0, 0, 0, 0, 500, 2]
    :param big_int: The integer you want to convert (Integer)
    :param bit: The length in bits of the representation (Integer)
    :param block_size: The initial size of each integer in the final array in bits (Integer)
    :return: An array representation of the integer (Integer Array)
    """
    str = intToBitString(big_int, bit)
    int_array = bitStringToIntArray(str)
    return int_array

def convertIntArraytoBigInt(int_array, block_size = 32):
    """
    Converts an integer array (of :block_size: bits, signed) into an integer
    (e.g. [0, 0, 0, 0, 0, 0, 1, 0] --> 2147483648)
    :param int_array: The integer array you want to convert (Integer array)
    :param block_size: Size, in bits, of the length of each integer in the array (Integer)
    :return: Integer representation of the array (Integer)
    """
    bin_str = ""
    for i in range(len(int_array)):
        bin_str += intToBitString(int_array[i], block_size)[1:]
    big_int = int(bin_str, 2)
    return big_int

if __name__ == "__main__":
    max = 2147483647 # 2^31 (signed)
    A = [0, 0, 0, 0, 0, 0, 1, 0]
    B = [0, 0, 0, 0, 0, 0, 0, max]
    P = [0, 0, 0, 2, 0, 0, 1, 0]
    print(convertBigIntToIntArray(max + 1))
    print(convertIntArraytoBigInt(A))