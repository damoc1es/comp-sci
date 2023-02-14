#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
 
int main() {
    int s;
    struct sockaddr_in server, client;
    
    s = socket(AF_INET, SOCK_DGRAM, 0);
    if (s < 0) {
        printf("Error at server socket creation\n");
        return 1;
    }
    
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    
    if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Bind error\n");
        return 1;
    }
    
    int l = sizeof(client);
    memset(&client, 0, sizeof(client));
    
    char str[100], rev[100];
    memset(str, 0, sizeof(str));
    memset(rev, 0, sizeof(rev));
    recvfrom(s, str, sizeof(str), 0, (struct sockaddr *) &client, &l);

    for(int i=0; i<strlen(str); i++) {
        rev[strlen(str)-i-1] = str[i];
    }

    sendto(s, rev, sizeof(rev), 0, (struct sockaddr *) &client, sizeof(client));
    
    close(s);
}