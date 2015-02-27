package com.baiyun.util.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtil {
	
	public static void replaceNormal(Fragment fragment, FragmentManager fragmentManager, int id) {
		fragmentManager.beginTransaction().replace(id, fragment).commit();
	}
	
	public static void replaceNormal(Fragment fragment, FragmentManager fragmentManager, int id, Bundle args) {
		fragment.setArguments(args);
		fragmentManager.beginTransaction().replace(id, fragment).commit();
	}
	
	public static void replaceAddToBack(Fragment fragment, FragmentManager fragmentManager, int id) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(id, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
				
	}
	
	public static void replaceAddToBack(Fragment fragment, FragmentManager fragmentManager, int id, Bundle args) {
		fragment.setArguments(args);
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(id, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
