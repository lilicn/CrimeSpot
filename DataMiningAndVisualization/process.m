clc
% 1:ARSON  2:ASSAULT 3:BURGLARY Ò¹µÁ ÈëÊÒµÁÇÔ
% 4:DISTURBING THE PEACE  5:DRUGS/ALCOHOL VIOLATIONS
% 6:DUI  7:FRAUD  8:HOMICIDE   9:MOTOR VECHICLE THEFT
% 10:ROBBERY 11:SEX CRIMES 12:THEFT/LARCENY  
% 13:VANDALISM Ëð»µ¹«Îï 14:VEHICLE BREAK-IN/THEFT 15:WEAPONS

type = {'ARSON','ASSAULT','BURGLARY','DISTURBING THE PEACE','DRUGS/ALCOHOL VIOLATIONS','DUI','FRAUD','HOMICIDE',...
    'MOTOR VEHICLE THEFT','ROBBERY','SEX CRIMES','THEFT/LARCENY','VANDALISM','VEHICLE BREAK-IN/THEFT','WEAPONS'};
counts = zeros(15,1);

data = zeros(size(raw,1),5); %type,lat,lng, hour, day
day = 1;
dayofMonth = 18;
for i = 1:size(raw,1)
    for j = 1:15
      if(strcmp(raw{i,1},type{j})==1)
          data(i,1) = j;
          counts(j,1) = counts(j,1)+1;
      end
    end
    data(i,2) = raw{i,3};
    data(i,3) = raw{i,4};
    
    t = strread(raw{i,2},'%s');
    [hour minute] = strread(t{2,1},'%d:%d');
    if(strcmp(t{3,1},'AM')==1)
      time = mod(hour,12)+1;
    elseif(strcmp(t{3,1},'PM')==1)
      time = mod(hour,12)+13;
    end
    data(i,4) = time;
    
    [month d year] = strread(t{1,1},'%d/%d/%d');
    if(d-dayofMonth==1 || (d~=dayofMonth && d==1) )
        dayofMonth = d;
        day = day +1;
    end
    data(i,5) = day;
end

maxlat = max(data(:,2))
minlat = min(data(:,2))
maxlng = max(data(:,3))
minlng = min(data(:,3))