from django.shortcuts import render
from . serializers import Signupserializer,LoginSerilaizer

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
# Create your views here.
@api_view(['POST'])
def SignupUser(request):
    serializer_class=Signupserializer
    queryset=User.objects.all()
   
    
        
    u_name=request.data['username']
    f_name=request.data['first_name']
    l_name=request.data['last_name']
    passw=request.data['password']
    e_mail=request.data['email']
    print(u_name)
    try: 
        User.objects.get(username=u_name)

        return JsonResponse('Username Already exists',safe=False)
    except:
            
            
        u=User(username=u_name,first_name=f_name,last_name=l_name,email=e_mail)
        u.set_password(passw)
        u.save()
        n_user=User.objects.get(username=u_name)
        
        return JsonResponse('loggedIn',safe=False)


@api_view(['POST'])
def LoginUser(request):
    serializer_class=Signupserializer
    queryset=User.objects.all()

    u_name=request.data['username']
    passw=request.data['password']
    user=authenticate(username=u_name,password=passw)
    if user is not None:
        user=User.objects.get(username=u_name)
        login(request,user)
        return JsonResponse("Logged In",safe=False)
    else:
        return JsonResponse("None",safe=False)



                

            

