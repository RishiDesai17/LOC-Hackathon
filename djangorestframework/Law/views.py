from django.shortcuts import render
from django.contrib import admin
from django.urls import path
from . import views
from . models import Law
from django.contrib.auth import authenticate,login
from django.contrib.auth.models import User
import json
from django.views.decorators.csrf import csrf_exempt
from rest_framework.parsers import JSONParser
from rest_framework.permissions import IsAuthenticated
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from . serializers import Lawserializer

from rest_framework import generics
# Create your views here.

class ViewLaws(generics.ListAPIView):
    serializer_class=Lawserializer
    queryset=Law.objects.all()


