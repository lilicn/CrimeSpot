% 1:ARSON  2:ASSAULT 3:BURGLARY Ò¹µÁ ÈëÊÒµÁÇÔ
% 4:DISTURBING THE PEACE  5:DRUGS/ALCOHOL VIOLATIONS
% 6:DUI  7:FRAUD  8:HOMICIDE   9:MOTOR VECHICLE THEFT
% 10:ROBBERY 11:SEX CRIMES 12:THEFT/LARCENY  
% 13:VANDALISM Ëğ»µ¹«Îï 14:VEHICLE BREAK-IN/THEFT 15:WEAPONS

weight = [0.1, 0.2, 0.2, ...
          0.1, 0.1, ...
          0.1, 0.1, 0.5, 0.2, ...
          0.2, 0.2, 0.2,...
          0.1, 0.2, 0.2];
score = zeros(m,n);

for k=1:size(data,1)
    if(data(k,2)>=minlat && data(k,2)<=maxlat && data(k,3)>=minlng && data(k,3)<=maxlng)
        i = ceil(abs(data(k,2)-maxlat)/latstep);
        j = ceil(abs(data(k,3)-minlng)/lngstep);
        score(i,j) = score(i,j) + weight(data(k,1));
    end
end
score = score/max(score(:))*100;