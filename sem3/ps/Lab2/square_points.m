function fr=square_points(points=2000, graph=false, task=1, see_all_points=false)
    if graph
        clf; hold on; axis equal;
        rectangle('Position', [0 0 1 1])
    endif


    C = [0.5 0.5]; % center
    O = [0 0]; % (0, 0)
    corners = [0 0 ; 0 1 ; 1 1 ; 1 0];

    k1 = 0;
    k2 = 0;
    k3 = 0;
    for i=1:points
        P=[rand() rand()];
        if graph && see_all_points
            plot(P(1), P(2), '*p', 'MarkerSize', 5)
        endif

        % i) inside circle
        if pdist([C;P]) < 0.5
            k1++;
            if graph && task == 1
                plot(P(1), P(2), '*p', 'MarkerSize', 5)
            endif
        endif

        % ii) closer to center
        ok = true;
        for j=1:4
            if pdist([corners(j, :);P]) < pdist([C;P])
                ok = false;
                break
            endif
        endfor
        if ok
            k2++;
            if graph && task == 2
                plot(P(1), P(2), '*p', 'MarkerSize', 5)
            endif
        endif

        % iii) creates 2 acute and 2 obtuse triangles
        obtuse = 0;
        acute = 0;

        corners = [corners ; 0 0];

        for j=1:4
            t = check_triangle(P, corners(j, :), corners(j+1, :));
            if t == 1
                acute++;
            elseif t == -1
                obtuse++;
            endif
        endfor

        if obtuse == 2 && acute == 2
            k3++;
            if graph && task == 3
                plot(P(1), P(2), '*p', 'MarkerSize', 5)
            endif
        endif
    endfor

    printf("i) inside circle = \x1B[36m%d\x1B[0m vs. ideal = %d\n", k1/points, pi/4)
    printf("ii) closer to center = \x1B[36m%d\x1B[0m vs. ideal = %d\n", k2/points, 0.5)
    printf("iii) creates 2 acute and 2 obtuse triangles = \x1B[36m%d\x1B[0m vs. ideal = %d\n", k3/points, 1-2*(1-pi*0.25))
    % circle = piR^2 = pi*0.25; outside of circle = 1-pi*0.25
    % 1-2*(1-pi*0.25)

    fr = [k1/points k2/points k3/points];
end
