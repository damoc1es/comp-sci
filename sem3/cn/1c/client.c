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
        printf("Error at client socket creation\n");
        return 1;
    }
    
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("172.20.48.56");
    
    char str[100];
    memset(str, 0, sizeof(str));

    printf("str = ");
    scanf("%99[^\n]", &str);

    sendto(c, str, sizeof(str), 0, (struct sockaddr *) &server, sizeof(server));
    uint16_t k=0;

    int l = sizeof(server);
    recvfrom(c, &k, sizeof(k), 0, (struct sockaddr *) &server, &l);
    k = ntohs(k);

    printf("Number of space characters: %hu\n", k);
    close(c);
}