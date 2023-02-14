#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
 
void serve_client(int c) {
    char str[100], rev[100];
    memset(str, 0, sizeof(str));
    memset(rev, 0, sizeof(rev));
    
    recv(c, str, sizeof(str), 0);

    for(int i=0; i<strlen(str); i++) {
        rev[strlen(str)-i-1] = str[i];
    }
    
    send(c, rev, sizeof(rev), 0);

    close(c);
}
 
int main() {
    int s;
    struct sockaddr_in server, client;
    int c, l;
    
    s = socket(AF_INET, SOCK_STREAM, 0);
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
    
    listen(s, 5);
    
    l = sizeof(client);
    memset(&client, 0, sizeof(client));
  
    while (1) {
        c = accept(s, (struct sockaddr *) &client, &l);
        printf("A client connected.\n");
        if (fork() == 0) {
            serve_client(c);
            return 0;
        }
    }
}