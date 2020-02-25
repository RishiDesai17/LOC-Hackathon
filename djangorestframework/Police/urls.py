from django.contrib import admin
from django.urls import path
from . import views
urlpatterns = [
    path('NewCrime',views.NewCrime.as_view(),name='NewCrime'),
    path('records',views.records,name='records'),
    path('sendmail',views.sendmail,name='sendmail'),
    path('verifyFIR/<int:obj_id>',views.verifyFIR,name='verifyFIR'),
    path('CreateFIR',views.CreateFIR,name='CreateFIR'),
    path('ViewFIR',views.ViewFIR.as_view(),name='ViewFIR'),
    path('PoliceVerifyFIR/<int:obj_id>',views.PoliceVerifyFIR,name='PolieVerify'),
    path('VerifiedFromChowki',views.VerifiedFromChowki.as_view(),name='VerifiedFromChowki'),
    path('DetailFIR/<int:pk>',views.DetailFIR.as_view(),name='DetailFIR'),


    
]
