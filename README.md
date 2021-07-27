# Memory management project that maps binary virtual addresses to physical addresses using the following page table: {2,4,1,7,3,5,6}

This program reads in a binary file of virtual address values using an Input stream and a byte array. It converts them to binary strings and loads them into an Arraylist. 
The offset and page bits are taken for each entry. The page bits are converted to a decimal value used to look up the frame number in the page table. 
The frame number is converted to a 5-bit binary string which is concatenated to the beginning of the offset to make a physical address. 
The physical address is converted to a hexadecimal string and written to the file "output.txt".
