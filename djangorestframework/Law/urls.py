from django.contrib import admin
from django.urls import path
from . import views
urlpatterns = [
    path('ViewLaws',views.ViewLaws.as_view(),name='ViewLaws'),
    
    
]
