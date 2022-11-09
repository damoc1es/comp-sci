#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
 
int main() {
    int c;
    struct sockaddr_in server;
    
    c = socket(AF_INET, SOCK_DGRAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socketului client\n");
        return 1;
    }
    
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("192.168.128.123");
    
    char str[100], rev[100];
    memset(str, 0, sizeof(str));
    memset(rev, 0, sizeof(rev));

    printf("str = ");
    scanf("%99[^\n]", &str);

    sendto(c, str, sizeof(str), 0, (struct sockaddr *) &server, sizeof(server));

    int l = sizeof(server);
    recvfrom(c, rev, sizeof(rev), 0, (struct sockaddr *) &server, &l);

    printf("The reversed string is %s\n", rev);
    close(c);
}