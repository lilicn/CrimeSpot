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
fileID = fopen('frequency.txt','w');
file2 = fopen('type.txt','w');

timespan =30;
series = zeros(m*n,ceil(181/timespan));
typeCount = zeros(m*n,size(type,2));
for k=1:size(data,1)
     if(data(k,2)>=minlat && data(k,2)<=maxlat && data(k,3)>=minlng && data(k,3)<=maxlng)
        i = ceil(abs(data(k,2)-maxlat)/latstep);
        j = ceil(abs(data(k,3)-minlng)/lngstep);
        id = (i-1)*n + j;
        t = ceil(data(k,5)/timespan);
        series(id,t) = series(id,t) + 1;
        typeCount(id,data(k,1)) = typeCount(id,data(k,1))+ 1;
     end
end
% plot(series) 
for i=1:m*n
   fprintf(fileID,'%d %d %d %d %d %d \n',series(i,1),series(i,2),series(i,3),series(i,4),series(i,5),series(i,6));
end

for i=1:m*n
    for j=1:size(type,2)
       fprintf(file2,'%d ',typeCount(i,j));
    end
    fprintf(file2,'\n');
end

fclose(fileID);
fclose(file2);