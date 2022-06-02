#include<stdio.h>

/*
Program to implement a scientific calculator 
*************************************************************** 
* Author 	Dept. 		Date	 	Notes 
****************************************************************
* Holly S	Economics  	Nov 4 2020 	Initial version.
* Holly S	Economics 	Nov 4 2020	Updated 
*/

int main (int argc, char *argv[]){
	if(argc != 4) {
		printf("Error: invalid number of arguments!\n");
		printf("scalc <operand1> <operator> <operand2>\n");
		return 1;
	}
	else if(*argv[2] != '+'){
		printf("Error: operator can only be + !\n");
                return 1;
	}
	int i;
	for(i = 0; argv[1][i]!='\0';i++){
                char dig = argv[1][i];
                if(dig<48 || dig>57){
                        printf("Error!! operand can only be positive integers\n");
                        return 1;
                }
        }
	int j;
	for(j = 0; argv[3][j]!='\0';j++){
                char dig = argv[3][j];
                if(dig < 48 || dig > 57){
                        printf("Error!! operand can only be positive integers\n");
                        return 1;
                }
        }
	//save largest of i or j for later
	int length;
	if(j>i){
		length = j;
	} else {
		length = i;
	}
	//use i and j to get to the digit
	int carryover = 0;
	char ans[1000];
	i--;
	j--;
	int lastdigit = length;
	while(i>=0 && j>=0){
		char current = argv[1][i] + argv[3][j] - 48 + carryover;
		if(current>57){
			carryover = 1;
			current=current-10;
		} else {
			carryover = 0;
		}
		ans[lastdigit]=current;
		i--, j--, lastdigit--;
	}
	// catch left over stuff
	while(i>=0){
		char current = argv[1][i] + carryover;
                if(current>57){
                        carryover = 1;
                        current=current-10;
                } else {
                        carryover = 0;
                }
                ans[lastdigit]=current;
		i--, lastdigit--;
	}
	while(j>=0){
                char current = argv[3][j] + carryover;
                if(current>57){
                        carryover = 1;
                        current=current-10;
                } else {
                        carryover = 0;
                }
                ans[lastdigit]=current;
                j--, lastdigit--;
        }

	//print
	if(carryover==1){
		ans[0]=49;
		for(int k = 0; k<=length; k++){
			printf("%c", ans[k]);
		}	
	}
	else {
		for(int k = 1; k<=length; k++){
                        printf("%c", ans[k]);
		}
	}
	printf("\n");
	return 0;
}
