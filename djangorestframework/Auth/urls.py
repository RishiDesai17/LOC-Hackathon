from django.contrib import admin
from django.urls import path
from . import views
urlpatterns = [
    path('signup',views.SignupUser,name='signup'),
    path('login',views.LoginUser,name='loginuser')
    
]
