#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
 
int main(int argc, char* argv[]) {
    int c;
    struct sockaddr_in server;
    
    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Error at client socket creation\n");
        return 1;
    }
    
    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    if(argc < 2) {
        server.sin_port = htons(1234);
        server.sin_addr.s_addr = inet_addr("127.0.0.1");
    } else {
        server.sin_port = htons(atoi(argv[2]));
        server.sin_addr.s_addr = inet_addr(argv[1]);
    }
    
    if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Error connecting to the server\n");
        return 1;
    }
    
    char str[100], rev[100];
    memset(str, 0, sizeof(str));
    memset(rev, 0, sizeof(rev));
    
    printf("str = ");
    scanf("%99[^\n]", &str);
    
    send(c, str, sizeof(str), 0);
    recv(c, rev, sizeof(rev), 0);
    
    printf("The reversed string is %s\n", rev);
    close(c);
}
