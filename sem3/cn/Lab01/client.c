#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
 
int main() {
    int c;
    struct sockaddr_in server;
    uint16_t a, b, suma;
    
    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Error at client socket creation\n");
        return 1;
    }
    
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("127.0.0.1");
    
    if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Error connecting to the server\n");
        return 1;
    }
    
    char str[100], rev[100];
    memset(str, sizeof(str), 0);
    memset(rev, sizeof(rev), 0);
    
    printf("str = ");
    scanf("%99[^\n]", &str);
    
    send(c, &str, sizeof(str), 0);
    recv(c, &rev, sizeof(rev), 0);
    
    printf("The reversed string is: %s\n", rev);
    close(c);
}
