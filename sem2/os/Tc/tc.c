#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>

int main() {
    char* s[3];
    int i, fd, offset;

    for(i=0; i<3; i++) {
        s[i] = (char*)malloc(3*sizeof(char));
        if(fgets(s[i], 128, stdin) == NULL) {
            perror("Error reading the sentence");
            return 1;
        }
    }

    offset = 'a' - 'A';
    for(i=0; i<3; i++) {
        for(int j=0; j<strlen(s[i]); j++) {
            if(s[i][j] >= 'a' && s[i][j] <= 'z') {
                s[i][j] -= offset;
            }
            else if(s[i][j] >= 'A' && s[i][j] <= 'Z') {
                s[i][j] += offset;
            }
        }
    }

    fd = open("out.txt", O_CREAT | O_TRUNC | O_WRONLY, 0600);
    if(fd < 0) {
        perror("Failed to create the output file");
        return 1;
    }
    for(i=0; i<3; i++) {
        write(fd, s[i], strlen(s[i]));
    }
    close(fd);

    for(i=0; i<3; i++) {
        free(s[i]);
    }
    return 0;
}