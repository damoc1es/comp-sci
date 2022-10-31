function chance=simulate_bdays(N=1000, people=23)
    favorable=0;
    for i=1:N
        bdays=randi(365, 1, people);
        k=0;

        w = sort(bdays);
        for i=2:length(w)
            if w(i) == w(i-1)
                k++;
                break;
            endif
        endfor

        if k > 0
            favorable++;
        endif
    endfor
    chance = favorable/N;
    printf("%d%%\n", chance*100);
end
