#include <Windows.h>
#include <stdio.h>
#include <tchar.h>
#include <string.h>
#include <iostream>
#include "TopSet.h" 
using namespace std;
int Cnum = 0; 
BOOL CALLBACK EnumChildWindowsProc(HWND hWnd,LPARAM lParam)
{
    char WindowTitle[100]={0};  
    Cnum++;
    ::GetWindowText(hWnd,WindowTitle,100);
    SetForegroundWindow(hWnd);
//    printf("--|%d :%s\n",Cnum,WindowTitle);
    return true;   
}
EXTERN_C __declspec(dllexport) void TopSetQQ()
{    
    HWND hd=GetDesktopWindow();        //得到桌面窗口
    hd=GetWindow(hd,GW_CHILD);        //得到屏幕上第一个子窗口
    char s[200]={0};
    int num=1;
    char name[10] = {"QQ"} ;
    while(hd!=NULL)                    //循环得到所有的子窗口
    {
        memset(s,0,200);
        GetWindowText(hd,s,200);
//        cout<<num++<<": "<<s<<endl;
        if(!strcmp(s,name))
        {
			ShowWindow(hd,1);
//			::SetWindowPos(hd,HWND_TOPMOST,0,0,0,0,SWP_NOMOVE|SWP_NOSIZE) ;
			EnumChildWindows(hd,EnumChildWindowsProc,NULL); //获取父窗口的所有子窗口
			return;
		}	
        	
        hd=GetNextWindow(hd,GW_HWNDNEXT);
    }
    return;
}
