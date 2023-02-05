package com.example.fileexplorer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String path;
    private Button but1;
    private Button but2;
    private Button but3;
    private Button but4;
    private ListView lv;
    private TextView textView;
    private String myFiless;
    private List<String> myFilesList;
    ArrayList<Product> fileses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        but1 = (Button) findViewById(R.id.button1);
        but2 = (Button) findViewById(R.id.button2);
        but3 = (Button) findViewById(R.id.button3);
        but4 = (Button) findViewById(R.id.button4);
        textView = (TextView) findViewById(R.id.pomId2);
        myFilesList = new ArrayList<String>();
        String newString;

        File newFile1 = null;
        File newFile2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            myFiless = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/com.example.fileexplorer/files/file";
            newFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/com.example.fileexplorer");
            newFile2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/com.example.fileexplorer/files");
        }
        else
        {
            myFiless = Environment.getExternalStorageDirectory() + "/DOCUMENTS/com.example.fileexplorer/files/file";
            newFile1 = new File(Environment.getExternalStorageDirectory() + "/DOCUMENTS/com.example.fileexplorer");
            newFile2 = new File(Environment.getExternalStorageDirectory() + "/DOCUMENTS/com.example.fileexplorer/files");
        }
        if(newFile1.exists()==false) {
            newFile1.mkdirs();
            if (newFile2.exists()==false) {
                newFile2.mkdirs();
            }
        }
        //test
        if(newFile1.exists()==false) {
            newFile1.mkdirs();
            if (newFile2.exists()==false) {
                newFile2.mkdirs();
            }
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            ArrayList<String> test = getIntent().getStringArrayListExtra("test");
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("STRING_I_NEED");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        path = newString;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        } else {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                recreate();
            }
        }
//copy
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFilesList.clear();
                for (int i = 0; i < fileses.size(); i++) {
                    if (fileses.get(i).getChecked()) {
                        myFilesList.add(fileses.get(i).getPath());
                    }
                }
                final String myFiles = myFiless;
                try {
                    File myObj = new File(myFiles);
                    myObj.delete();
                    myObj.createNewFile();
                    FileOutputStream fos = new FileOutputStream(myObj);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write("copy");
                    bw.newLine();
                    for (int i=0; i < myFilesList.size(); i++) {
                        bw.write(myFilesList.get(i));
                        bw.newLine();
                    }
                    bw.close();
                } catch (IOException e) {
                    Log.e("MYAPP", "exception", e);
                }
            }
        });
//move
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFilesList.clear();
                for (int i = 0; i < fileses.size(); i++) {
                    if (fileses.get(i).getChecked()) {
                        myFilesList.add(fileses.get(i).getPath());
                    }
                }
                final String myFiles = myFiless;
                try {
                    File myObj = new File(myFiles);
                    myObj.delete();
                    myObj.createNewFile();
                    FileOutputStream fos = new FileOutputStream(myObj);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write("move");
                    bw.newLine();
                    for (int i=0; i < myFilesList.size(); i++) {
                        bw.write(myFilesList.get(i));
                        bw.newLine();
                    }
                    bw.close();
                } catch (IOException e) {
                    Log.e("MYAPP", "exception", e);
                }
            }
        });
        //paste
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileInputStream is;
                BufferedReader reader;
                final File file = new File(myFiless);
                Boolean toDelete = false;

                if (file.exists()) {
                    try {
                        is = new FileInputStream(file);
                        reader = new BufferedReader(new InputStreamReader(is));
                        String line = reader.readLine();
                        if (line!=null) {
                            if (line.equals("move")) {
                                toDelete = true;
                            }
                            if (line.equals("copy")) {
                                toDelete = false;
                            }
                            line = reader.readLine();
                        }
                        while(line != null){
                            if (new File(line).isDirectory()==false) {
                                String[] path3 = line.split("/");
                                String inputPath = "";
                                String outputPath = path;
                                String inputFile = path3[path3.length - 1];
                                for (int i = 1; i < path3.length - 1; i++) {
                                    inputPath = inputPath + "/" + path3[i].toString();
                                }

                                InputStream in = null;
                                OutputStream out = null;
                                try {
                                    in = new FileInputStream(inputPath + "/" + inputFile);
                                    out = new FileOutputStream(outputPath + "/" + inputFile);

                                    byte[] buffer = new byte[1024];
                                    int read;
                                    while ((read = in.read(buffer)) != -1) {
                                        out.write(buffer, 0, read);
                                    }
                                    in.close();
                                    in = null;

                                    // write the output file
                                    out.flush();
                                    out.close();
                                    out = null;

                                    // delete the original file
                                    if (toDelete==true) {
                                        new File(inputPath + "/" + inputFile).delete();
                                    }

                                } catch (FileNotFoundException f) {
                                    Log.e("MYAPP", "exception", f);
                                    Toast.makeText(getApplicationContext(), "You moved this file earlier.", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Log.e("MYAPP", "exception", e);
                                }
                            }
                            if (new File(line).isDirectory()==true) {
                                somethingDir(new File(line),toDelete);
                            }

                            line = reader.readLine();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.e("MYAPP", "exception", e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("MYAPP", "exception", e);
                    }
                }
                Intent result = new Intent(MainActivity.this, MainActivity.class);
                result.putExtra("STRING_I_NEED", path);
                Intent intent = getIntent();
                startActivity(result);
            }
        });
//delete
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < fileses.size(); i++) {
                    if (fileses.get(i).getChecked()) {
                        File file = new File(fileses.get(i).getPath());
                        if (file.isDirectory()==true) {
                            deleteDir(file);
                        }

                        else {
                            file.delete();
                            if (file.exists()) {
                                try {
                                    file.getCanonicalFile().delete();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.e("MYAPP", "exception", e);
                                }
                                if (file.exists()) {
                                    getApplicationContext().deleteFile(file.getName());
                                }
                            }
                        }
                    }
                }
                Intent result = new Intent(MainActivity.this, MainActivity.class);
                result.putExtra("STRING_I_NEED", path);
                Intent intent = getIntent();
                startActivity(result);
            }
        });

        if (path == null) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        }
        GetFiles(path);

        final CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.custom_list_adapter, fileses);

        lv.setAdapter(adapter);

        lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Product p1 = (Product) parent.getItemAtPosition(position);
                Boolean p2 = p1.getDirectory();
                if (p2 == true) {
                    Intent result = new Intent(MainActivity.this, MainActivity.class);
                    String path2 = path + "/" + p1.getName();
                    result.putExtra("STRING_I_NEED", path2);
                    Intent intent = getIntent();
                    startActivity(result);
                } else {
                    String line = p1.getName().toString();
                    File file = new File(path + "/" + line);

                    // Get URI and MIME type of file
                    //Uri uris = Uri.fromFile(file);
                    Uri uris = FileProvider.getUriForFile(getApplicationContext(), "com.example.fileexplorer.fileprovider", file);
                    String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uris.toString());
                    String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

                    try {
                        // Open file with user selected app
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uris, mime);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "A PROBLEM OCCURRED\nYou don't have an application to open this file\n\n"+line, Toast.LENGTH_LONG).show();
                        Log.e("MYAPP", "exception", e);
                    }
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id)
            {
                View x = lv.getChildAt(position-lv.getFirstVisiblePosition());
                //Toast.makeText(MainActivity.this, "LongClicked:"+ Integer.toString(position), Toast.LENGTH_LONG).show();
                if (lv.isItemChecked(position)==true) {
                    lv.setItemChecked(position,false);
                    Product p = fileses.get(position);
                    p.setChecked(false);
                    fileses.set(position,p);
                    return true;
                } else {
                    lv.setItemChecked(position,true);
                    Product p = fileses.get(position);
                    p.setChecked(true);
                    fileses.set(position,p);
                    return true;
                }
            }
        });

    }

    public void GetFiles(String DirectoryPath) {
        ArrayList<Product> MyFiles = new ArrayList<Product>();
        File f = new File(DirectoryPath);
        File[] files = f.listFiles();
        try {
            if (files.length == 0) {
                textView.setVisibility(View.VISIBLE);
                fileses = MyFiles;
            } else {
                textView.setVisibility(View.GONE);
                for (int i = 0; i < files.length; i++) {
                    String pom = files[i].getName();
                    String pom2 = ".thumbnails";
                    String pom3 = ".nomedia";
                    Boolean x = pom.equals(pom2);
                    Boolean y = pom.equals(pom3);
                    if (y == false && x == false) {
                        if (files[i].isDirectory() == true) {
                            MyFiles.add(new Product(true, files[i].getName(), files[i].getAbsolutePath(),false));
                        } else {
                            MyFiles.add(new Product(false, files[i].getName(), files[i].getAbsolutePath(),false));
                        }
                    }
                }
            }

        } catch (NullPointerException e) {
        }
        Collections.sort(MyFiles, Product.ProductNameComparator);
        fileses = MyFiles;
    }

    void somethingDir(File file, Boolean toDelete) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                String[] path3 = f.getPath().split("/");
                String inputPath = "";
                String outputPath = path+f.getPath().replace("/storage/emulated/0","");
                String inputFile = path3[path3.length - 1];
                for (int i = 1; i < path3.length - 1; i++) {
                    inputPath = inputPath + "/" + path3[i].toString();
                }
                File dir = new File (outputPath);
                if (file.isDirectory() && !dir.exists()) {
                    dir.mkdirs();
                }
                else {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = new FileInputStream(inputPath + "/" + inputFile);
                        out = new FileOutputStream(outputPath + "/" + inputFile);

                        byte[] buffer = new byte[1024];
                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }
                        in.close();
                        in = null;

                        // write the output file
                        out.flush();
                        out.close();
                        out = null;

                        // delete the original file
                        if (toDelete) {
                            new File(inputPath + "/" + inputFile).delete();
                        }

                    } catch (FileNotFoundException fi) {
                        Log.e("MYAPP", "exception", fi);
                    } catch (Exception e) {
                        Log.e("MYAPP", "exception", e);
                    }
                }



                somethingDir(f,toDelete);
            }
        }
        if (toDelete) {
            file.delete();
        }
    }

    void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent(MainActivity.this, MainActivity.class);
        String path2 = path;
        String absolutePath = "/storage/emulated/0";
        String[] path3=path2.split("/");
        String path4="";
        for (int i=1; i<path3.length-1; i++) {
            path4=path4+"/"+path3[i].toString();
        }
        if (path4.contains(absolutePath)) {
            result.putExtra("STRING_I_NEED", path4);
        }
        else result.putExtra("STRING_I_NEED", absolutePath);
        Intent intent = getIntent();
        startActivity(result);
        super.onBackPressed();
    }
}