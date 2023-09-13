#!/bin/bash

# trova tutte le recensioni 

echo "# trova tutte le recensioni" 
echo $(curl -s 10.10.10.10:8080/recensioni/recensioni)
echo 

