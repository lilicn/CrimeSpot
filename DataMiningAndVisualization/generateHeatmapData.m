fileID = fopen('heatmap.txt','w');
for k=1:size(data,1)
    if(data(k,2)>=minlat && data(k,2)<=maxlat && data(k,3)>=minlng && data(k,3)<=maxlng)
       % fprintf(fileID,'new google.maps.LatLng(%.6f, %.6f),\n',data(k,2),data(k,3));
       % weighted
       fprintf(fileID,'{location: new google.maps.LatLng(%.6f, %.6f), weight:%.2f},\n',data(k,2),data(k,3),weight(data(k,1)));
    end
end
fclose(fileID);