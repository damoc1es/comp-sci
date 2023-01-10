function problem(N=1000)
    X = unifrnd(1, 2, 1, N);
    Y = unifrnd(-1, 1, 1, N);

    k1 = 0; % X > 1,5 and Y < 0,5
    k2 = 0; % X > 1.5 or Y < 0.5

    for i=1:N
        if X(i) > 1.5 && Y(i) < 0.5
            k1 += 1;
        endif

        if X(i) > 1.5 || Y(i) < 0.5
            k2 += 1;
        endif
    endfor

    printf("a) estimation\nX > 1.5 and Y < 0.5 : %d\n", k1/N);
    printf("X > 1.5 or Y < 0.5 : %d\n", k2/N);

    % A: "X > 1.5" => 1/2 | not A => 1-1/2 = 1/2
    % B: "Y < 0.5" => 3/4 | not B => 1-3/4 = 1/4

    x = 1/2*3/4; % A and B
    y = 1/2*1/4; % A and (not B)
    z = 1/2*3/4; % (not A) and B

    printf("b) theoretical\nX > 1.5 and Y < 0.5 : %d\n", x);
    printf("X > 1.5 or Y < 0.5 : %d\n", x+y+z);
end
