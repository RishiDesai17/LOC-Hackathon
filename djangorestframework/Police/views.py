from django.shortcuts import render


from rest_framework import generics

from rest_framework.decorators import api_view
import json
from django.http import JsonResponse,HttpResponse
import requests

from django.contrib.auth import authenticate,login
from django.contrib.auth.models import User
import json
from django.views.decorators.csrf import csrf_exempt
from rest_framework.parsers import JSONParser
from rest_framework.permissions import IsAuthenticated
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from . serializers import NewCrimeserializer,FIRserializer,FIRserializer1
from . models import Crime,FIR
from django.core.mail import send_mail
import pdb

# Create your views here.
class NewCrime(generics.CreateAPIView):
    serializer_class=NewCrimeserializer
    queryset=Crime.objects.all()


@api_view(['POST'])
def records(request):
    crim_name=request.data['Criminal_ID']
    data=Crime.objects.filter(Name=crim_name)
    li=[]
    for x in data:
        li.append(x.Description)
    d=json.dumps(li)
    return JsonResponse(d,safe=False)

@api_view(['POST'])
def sendmail(request):
    Name=request.data['Name']
    location=request.data['Location']
    Timestamp=request.data['Timestamp']
    send_mail('Loan','{} located at {} on this time {}'.format(Name,location,Timestamp),'hrishikesh2pv@gmail.com',['rishindesai@yahoo.com'],fail_silently=False)
    return JsonResponse("Done",safe=False)

@api_view(['POST'])#Create FIR API - user
def CreateFIR(request):
    Name=request.data['Victim_Name']
    Complaint_1=request.data['Complaint']
    Aadhar=request.data['Aaadhar_No']
    Contact=request.data['Contact_No']
    email_1=request.data['email']
    chowki_1=request.data['chowki']
    f=FIR(Victim_Name=Name,Complaint=Complaint_1,Aaadhar_No=Aadhar,Contact_No=Contact,email=email_1,Chowki=chowki_1)
    f.save()
    send_mail('Verify that you have created FIR','http://127.0.0.1:8000/police/verifyFIR/{}'.format(f.id),'hrishikesh2pv@gmail.com',['rishindesai@yahoo.com'],fail_silently=False)
    return JsonResponse("Done",safe=False)


def  verifyFIR(request,obj_id):#Verify FIR - user
    f=FIR.objects.get(id=obj_id)
    f.User_Verified=True

    f.save()
    send_mail('Your have verified your FIR,check your loan here','http://127.0.0.1:8000/police/DetailFIR/{}'.format(obj_id),'hrishikesh2pv@gmail.com',['rishindesai@yahoo.com'],fail_silently=False)
    return HttpResponse("Done")


@api_view(['GET'])# verify FIR - Police
def PoliceVerifyFIR(request,obj_id):
    f=FIR.objects.get(id=obj_id)
    f.Police_Verified=True
    f.save()
    send_mail('POLICE HAS VERIFIED YOUR FIR','http://127.0.0.1:8000/police/DetailFIR/{}'.format(obj_id),'hrishikesh2pv@gmail.com',['rishindesai@yahoo.com'],fail_silently=False)
    return JsonResponse("Verified",safe=False)





class ViewFIR(generics.ListAPIView):#View FIR LIST
    serializer_class=FIRserializer
    queryset=FIR.objects.filter(User_Verified=True,Police_Verified=False)

class DetailFIR(generics.RetrieveAPIView):#View Particular FIR
    serializer_class=FIRserializer1
    queryset=FIR.objects.all()



class VerifiedFromChowki(generics.GenericAPIView):
    #View FIR verified from particular Chowki
    def post(self,request):
        place=request.data['Chowki']
        f=FIR.objects.filter(Police_Verified=True,Chowki=place)
        print(f)
        dict={}
        dict2={}
        li=[]
        for x in f:
            y=x.Victim_Name
            print(x)
            d={
                'Name':x.Victim_Name,
                
                'Complaint':x.Complaint,
                
                'Aadhar_No':x.Aaadhar_No,
                'email':x.email,
                'Chowki':x.Chowki,
                'Police_Verified':x.Police_Verified
            }
            li.append(d)

        dict2={
            'Details':li
        }
        json.dumps(dict2)
        return JsonResponse(dict2)




                
                
                
                

            
    
