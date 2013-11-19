clc


maxlat=36.19; minlat =36.138; latstep=0.004;   %445m
minlng= -86.85; maxlng=-86.72; lngstep=0.005; %449m
m = round((maxlat-minlat)/latstep);
n = round((maxlng-minlng)/lngstep);
grid=zeros(m,n);

count=0;
for k=1:size(data,1)
    if(data(k,2)>=minlat && data(k,2)<=maxlat && data(k,3)>=minlng && data(k,3)<=maxlng)
        count = count +1;
        i = ceil(abs(data(k,2)-maxlat)/latstep);
        j = ceil(abs(data(k,3)-minlng)/lngstep);
        id = (i-1)*n + j;
        grid(i,j) = grid(i,j) + 1;
    end
end

%visualize
a=8;b=15;
timespan =7;
series = zeros(ceil(181/timespan),1);
for k=1:size(data,1)
    i = ceil(abs(data(k,2)-maxlat)/latstep);
    j = ceil(abs(data(k,3)-minlng)/lngstep);
    if(a==i && b ==j)
        t = ceil(data(k,5)/timespan);
        series(t,1) = series(t,1) + 1;
    end
end
plot(series)        