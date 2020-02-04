def intToString(a, bit = 256):
    """
    Converts an integer to a binary string representation (without '0b')
    :param a: The integer to convert (integer)
    :param bit: The size of the representation in bit (integer)
    :return: Binary string representation (String)
    """
    str = bin(a)[2:]
    return (bit-len(str))*"0" + str

def bitStringToIntArray(str, block_size = 31):
    int_array = []
    for i in range(0, len(str), block_size):
        print("block =")
        int_array.append(int(str[i:i+block_size], 2))
    return int_array

if __name__ == "__main__":
    str_test = intToString(2147483647)
    print(str_test)
    int_array_test = bitStringToIntArray(str_test)
    print(int_array_test)
