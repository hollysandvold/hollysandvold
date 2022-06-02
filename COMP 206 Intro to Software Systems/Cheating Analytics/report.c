
/*
Program to check for same IP address given a student's name 
*************************************************************** 
* Author 	Dept. 		Date	 	Notes 
****************************************************************
* Holly S	Economics  	Nov 16 2020 	Initial version.
*/
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
int main (int argc, char *argv[]){
	FILE *filecopy=fopen(argv[1], "rt");
	FILE *txtfile=fopen(argv[3], "wt");
	if(argc != 4){ 
		printf("Usage ./report <csvfile> \"<student name>\" <reportfile>\n");
		return 1;
	} 
	else if(filecopy == NULL){
		printf("Error, unable to open csv file %s\n", argv[1]);
		return 1;
	}else if(txtfile == NULL){
		printf("Error, unable to open the output file %s\n", argv[3]);
		return 1;
	}
	
	// check for user and get their IP address
	const char s[2]= ",";
	char line[201];
	char *name;
	int notFound = 0;
	char *IP=(char *)malloc(50);

	while (fgets(line, sizeof(line), filecopy)){
		name =  strtok(line,s);
		if(strcmp(name,argv[2])==0){
			notFound = 1;
			strtok(NULL, s);
			strtok(NULL, s);
			strtok(NULL, s);
			strtok(NULL, s);
			strtok(NULL, s);
			IP = strtok(NULL, s);
			break;
		}	
	}
	
	//error if noone is found
	if(notFound==0){
		printf("Error, unable to locate %s\n", argv[2]);
		return 1;
	}
	fclose(filecopy);
	
	//find a collaborator
	FILE *filecopy2=fopen(argv[1], "rt");
	char *currentIP;
	char prevName[101];
	int cheatFound = 0;
	//save the final IP address somewhere else
	char final[51];
	for(int i = 0; i<51; i++){
		if(IP[i]=='\0'){
			final[i]='\0';
			break;
		}
		final[i]=IP[i];
	}
	//burn a line
	fgets(line, sizeof(line), filecopy2);
	while (fgets(line, sizeof(line), filecopy2)){
		//set name and then check for repeats
		name = strtok(line,s);
		if(strcmp(name, argv[2]) == 0){
			continue;
		}
		else if (strcmp(name, prevName)==0){
                       	continue;
		}

		//burn tokens to get to IP
		for (int i =1; i<=5; i++){
			strtok(NULL,s);
		}
		
		//set IP and print name to file
		currentIP=strtok(NULL,s);
		if(currentIP!=NULL){	
			if (strcmp(currentIP, final)==0){
				cheatFound = 1;
				fprintf(txtfile, "%s\n", name);
			}
		}
	
		//set prevName
		for(int i = 0; i<101; i++){
                	if(name[i]=='\0'){
                        	prevName[i]='\0';
                        	break;
                	}	
               		prevName[i]=name[i];
        	}	
	}
	if(cheatFound == 0){
		fprintf(txtfile, "No collaborators found for %s\n", argv[2]);
	}
	fclose(filecopy2);
	fclose(txtfile);
	return 0;
}
