/*
 * DesktopApplication1View.java
 */

package desktopapplication1;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.io.*;
import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Collection ;
import javax.swing.DefaultListModel;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTable;
import javax.persistence.Query;
import javax.imageio.*;
import java.util.Arrays;
import java.io.*;
import java.awt.image.*;
import gov.loc.repository.bagit.*;
import gov.loc.repository.bagit.v0_96.impl.*;
import com.sun.media.imageio.plugins.jpeg2000.J2KImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import au.edu.apsr.mtk.base.*;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.JFileChooser;
import com.jcraft.jsch.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import gov.loc.repository.bagit.driver.CommandLineBagDriver;
/**
 * The application's main frame.
 */
public class DesktopApplication1View extends FrameView {

    public DesktopApplication1View(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        entityManager =  javax.persistence.Persistence.createEntityManagerFactory("DesktopApplication1PU").createEntityManager();
        m1=(DefaultTableModel)jTable1.getModel();
       m2=(DefaultTableModel)jTable2.getModel();
       listener=new SelectionListener(jTable1,entityManager,m1,m2);
       fillTable(m1);
       jTable1.getSelectionModel().addListSelectionListener(listener);
       jTable1.getColumnModel().getSelectionModel().addListSelectionListener(listener);
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
       

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
            aboutBox = new DesktopApplication1AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication1.getApplication().show(aboutBox);
    }
     void fillTable(DefaultTableModel m1)
    {
        Query q=entityManager.createNamedQuery("Project.findAll");
        int count =0;
        Iterator result = q.getResultList().iterator();
         while(result.hasNext())
                {
                    Project pro = (Project) result.next();
                    Vector v= new Vector();
                    v.add(pro.getProjectid());
                    v.add(pro.getProjectname());
                    m1.addRow(v);
                    count++;
                }
         m1.fireTableRowsInserted(0,count );
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Project_id", "Project"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getResourceMap(DesktopApplication1View.class);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setResizable(false);
        jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Barcode", "TiffDate", "JP2", "Jpeg", "OCRed"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setColumnSelectionAllowed(true);
        jTable2.setName("jTable2"); // NOI18N
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable2.columnModel.title0")); // NOI18N
        jTable2.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable2.columnModel.title1")); // NOI18N
        jTable2.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable2.columnModel.title2")); // NOI18N
        jTable2.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable2.columnModel.title3")); // NOI18N
        jTable2.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable2.columnModel.title4")); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(8, 8, 8))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getActionMap(DesktopApplication1View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    ThreadPool pool = new ThreadPool(6);
    int rowIndexStart = jTable2.getSelectedRow();
    int rowIndexEnd = jTable2.getSelectionModel().getMaxSelectionIndex();
    int colIndexStart = jTable2.getSelectedColumn();
    int colIndexEnd = jTable2.getColumnModel().getSelectionModel()
        .getMaxSelectionIndex();

    // Check each cell in the range
    for (int r=rowIndexStart; r<=rowIndexEnd; r++)
    {
        for (int c=colIndexStart; c<=colIndexEnd; c++)
        {
            if (jTable2.isCellSelected(r, c))
            {
                // cell is selected

                System.out.println(" nested for loop iteration with r = "+r+" c = "+c);
                //int col=jTable2.getSelectedColumn();
                //int row=jTable2.getSelectedRow();
                System.out.println(jTable2.getValueAt(r, c));
                String bar = (String)jTable2.getValueAt(r, c);
                Book b=entityManager.find(Book.class, bar);
                Project p =b.getProjectid();
                String project_id=p.getProjectname();
                String Language=b.getLanguage();
                System.out.println(Language);
                String p1=b.getPath();
                String p2=b.getBarcode();

                if(Language.equals("ara") || Language.equals("heb"))
                {
                    //create the RAWJPEG2k and TIFFJPEG2K folders
                    String ipath="d:\\backup\\"+project_id+"\\"+p2+"\\RAWJPEG2k\\IMAGES";
                    java.io.File f=new java.io.File(ipath);
                    java.io.File pfile =new java.io.File ("d:\\backup\\"+p.getProjectname());
                    if (!pfile.exists())
                    {
                        boolean projres=( new java.io.File ("d:\\backup\\"+p.getProjectname()).mkdir());
                    }
                    java.io.File bfile =new java.io.File ("z:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode());
                    if(!bfile.exists())
                    {
                        boolean bookres=(new java.io.File ("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()).mkdir());
                    }
                    boolean rawjpeg=(new java.io.File ("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJpeg2k\\JPeg2k").mkdir());
                    System.out.println("images path is "+ipath);
                    java.io.File file=null;
                    String[] children=f.list();

                    if(rawjpeg==true)

                    {//Convert RAWs to TIFFs
                        for (int i = 0;i<children.length;i++)
                        {
                            if(children[i].endsWith("cr2"))
                            {
                                file = new java.io.File("d:\\backup\\"+project_id+"\\"+p2+"\\RAWJPEG2K\\IMAGES\\"+children[i]);
                                String PAth = "d:\\backup\\"+project_id+"\\"+p2+"\\RAWJpeg2k\\JPEG2k";
                                System.out.println(file.getAbsolutePath());


                                pool.assign(new TestWorkerThread(file,i,"RAW",PAth));

                            }

                        }
                        pool.complete();
                        entityManager.getTransaction().begin();
                        b.setJpc(Boolean.TRUE);
                        entityManager.getTransaction().commit();
                    }
                    else
                    {
                        System.out.println("Book folder "+ b.getBarcode()+"could not be created");
                    }
                    System.out.println("creating tiffs");
                    if(Language.equals("ara"))
                    {
                    file= new java.io.File("Z:\\ARA_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\images");
                    }
                    if(Language.equals("heb"))
                    {
                    file= new java.io.File("Z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\images");
                    }
                    System.out.println("the path for tiff file is "+ file.getAbsolutePath());
                    java.io.File tiffjp=new java.io.File ("d:\\Backup\\"+project_id+"\\"+p2+"\\TiffJpeg2k");
                    boolean tiffjpeg=true;
                    if(!tiffjp.exists())
                        tiffjpeg=tiffjp.mkdir();
              /*   try
                 {


                 java.io.File dstf = new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\tiffJPEG2K\\IMAGES");
                 copyDirectory(file,dstf );
                    }
                 catch(Exception e)
                 {
                     System.out.println(e);
                 }*/
                    children=file.list();
                    if(tiffjpeg)
                    {
                        boolean jpeg = true;
                        java.io.File jp=new java.io.File ("d:\\Backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TiffJpeg2k\\JPEG2k");
                        if(!jp.exists())
                                jp.mkdir();
                        if(jpeg)
                        {
                            for (int i = 0;i<children.length;i++)
                            {
                                System.out.println(children[i]);
                                if(children[i].endsWith("tif"))
                                {
                                    java.io.File file1 = new java.io.File(file.getAbsolutePath()+"\\"+children[i]);
                                    System.out.println(file1.getAbsolutePath());


                                    pool.assign(new TestWorkerThread(file1,i,"tiff","d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TiffJpeg2k\\jpeg2k"));

                                }

                            }
                            pool.complete();
                            entityManager.getTransaction().begin();
                            b.setJpeg(Boolean.TRUE);
                            entityManager.getTransaction().commit();
                        }
                        else
                        {
                            System.out.println("jpeg folder in tiff folder could not be created");
                        }
                    }
                    else
                    {
                        System.out.println("tiff folder could not be created");
                    }

                    if (Language.equals("heb"))
                        {
                    try
                    {

                        java.io.File f1 = new java.io.File("Z:\\HEB_FIN\\"+project_id+"\\"+p2 + "\\PDF\\"+p2+".pdf");
                        String path = "Z:\\HEB_FIN\\"+project_id+"\\"+p2+"\\PDF\\LowRes_"+p2+".pdf";
                        System.out.println("PDF file Path is "+f1.getAbsolutePath());
                        System.out.println("new file path is "+path);
                        System.out.flush();
                        Process theProcess = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Kirtas\\Setup1\\consoleapplication2.exe " +f1.getAbsolutePath() + " " + path );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }

theProcess.waitFor();
                        java.io.File f2= new java.io.File("z:\\HEB_FIN\\"+project_id+"\\"+p2+"\\PDF\\HighRes_"+p2+".pdf");
                        if(f1.renameTo(f2))
                            System.out.println(f1.getAbsolutePath()+" renamed successfully to "+f2.getAbsolutePath());
                        else
                            System.out.println(f1.getAbsolutePath()+" rename to "+f2.getAbsolutePath()+" failed");
                        System.out.println("C:\\Program Files (x86)\\Kirtas\\Setup1\\consoleapplication2.exe " +f1.getAbsolutePath() + " " + path );
                        System.out.println("waiting for the process CLcompr");
                        
                        System.out.println("waiting over for the process CLcompr");
                        entityManager.getTransaction().begin();
                        b.setOCRed(Boolean.TRUE);
                        entityManager.getTransaction().commit();
                        boolean check =(new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"TIFFJPEG2k\\PDF").mkdir());
                        java.io.File dir = new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"TIFFJPEG2K\\PDF");
                        // Move file to new directory
                        java.io.File newpdf= new java.io.File(path);
                        Process theProcess1 = Runtime.getRuntime().exec("cmd /c move "+ newpdf.getAbsolutePath() +" "+ dir.getAbsolutePath());
                        if(f2.exists())
                        {
                            //success = f2.renameTo(new java.io.File(dir, f2.getName()));
                            Process theProcess2 = Runtime.getRuntime().exec("cmd /c move "+ f2.getAbsolutePath() +" "+ dir.getAbsolutePath());
                        }
                        else
                            System.out.println(f2.getAbsolutePath()+"doesnt exist");

                    }
                    catch (Exception e)
                    {
                        System.out.println("Exception raised"+e);
                        e.printStackTrace();

                    }
                }
                    if (Language.equals("ara"))
                        {
                    try
                    {

                        java.io.File f1 = new java.io.File("Z:\\ARA_FIN\\" + project_id+"\\"+p2+"\\PDF\\"+p2+".pdf");
                        String path = "Z:\\ARA_FIN\\"+project_id+"\\"+p2+"\\PDF\\LowRes_"+p2+".pdf";
                        System.out.println("PDF file Path is "+f1.getAbsolutePath());
                        System.out.println("new file path is "+path);
                        System.out.flush();
                        Process theProcess = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Kirtas\\Setup1\\consoleapplication2.exe " +f1.getAbsolutePath() + " " + path );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                        theProcess.waitFor();

                        java.io.File f2= new java.io.File("z:\\ARA_FIN\\"+project_id+"\\"+p2+"\\PDF\\HighRes_"+p2+".pdf");
                        if(f1.renameTo(f2))
                            System.out.println(f1.getAbsolutePath()+" renamed successfully to "+f2.getAbsolutePath());
                        else
                            System.out.println(f1.getAbsolutePath()+" rename to "+f2.getAbsolutePath()+" failed");
                        System.out.println("C:\\Program Files (x86)\\Kirtas\\Setup1\\consoleapplication2.exe " +f1.getAbsolutePath() + " " + path );
                        System.out.println("waiting for the process CLcompr");
                        
                        System.out.println("waiting over for the process CLcompr");
                        entityManager.getTransaction().begin();
                        b.setOCRed(Boolean.TRUE);
                        entityManager.getTransaction().commit();
                        java.io.File Pdfdir =new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2k\\PDF");
                        if(!Pdfdir.exists())
                        {
                            Pdfdir.mkdir();
                            System.out.println(Pdfdir.getAbsolutePath()+" is created");
                        }
                        else
                            System.out.println(Pdfdir.getAbsolutePath()+" exists");

                        java.io.File dir = new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\PDF");
                        // Move file to new directory
                        java.io.File newpdf= new java.io.File(path);
                        if(newpdf.exists())
                        {
                            System.out.println("cmd /c move "+ newpdf.getAbsolutePath() +" "+ dir.getAbsolutePath());
                            Process theProcess1 = Runtime.getRuntime().exec("cmd /c move "+ newpdf.getAbsolutePath() +" "+ dir.getAbsolutePath());
                            theProcess1.waitFor();
                        }
                        else
                        {
                            System.out.println(newpdf.getAbsolutePath()+" Doesn't exist");
                        }

                        
                        if(f2.exists())
                        {
                            //success = f2.renameTo(new java.io.File(dir, f2.getName()));
                            System.out.println("cmd /c move "+ f2.getAbsolutePath() +" "+ dir.getAbsolutePath());
                            Process theProcess2 = Runtime.getRuntime().exec("cmd /c move "+ f2.getAbsolutePath() +" "+ dir.getAbsolutePath());
                            theProcess2.waitFor();
                            
                        }
                        else
                            System.out.println(f2.getAbsolutePath()+"doesnt exist");

                    }
                    catch (Exception e)
                    {
                        System.out.println("Exception raised"+e);
                        e.printStackTrace();

                    }
                }
                  
                    try
                    {
                        System.out.println("creating metadata");
                        java.io.File mfile= new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJPEG2K\\METADATA");
                        if(!mfile.exists())
                        {
                            mfile.mkdir();
                            System.out.println(mfile.getAbsolutePath()+ "is created");
                        }
                        else
                        {
                            System.out.println(mfile.getAbsolutePath()+"exists");
                        }
                        java.io.File mmfile= new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJPEG2K\\METADATA\\MIX");
                        if(!mmfile.exists())
                        {
                            mmfile.mkdir();
                        }
                        java.io.File tmfile= new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                        if(!tmfile.exists())
                            tmfile.mkdir();
                        java.io.File tmmfile= new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA\\MIX");
                        if(!tmmfile.exists())
                            tmmfile.mkdir();
                        java.io.File MetadataPath=null;
                        if(Language.equals( "ara"))
                            MetadataPath = new java.io.File("Z:\\ARA_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA");
                        else if(Language.equals("heb") )
                            MetadataPath = new java.io.File("Z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA");
                        if(MetadataPath!=null && MetadataPath.exists())
                        {
                            System.out.println("creating objects for DC MARC and MODS Files");
                            java.io.File DCf = new java.io.File(MetadataPath.getAbsolutePath()+"\\"+b.getBarcode()+"_DC.xml");
                            java.io.File MARCf = new java.io.File(MetadataPath.getAbsolutePath()+"\\"+b.getBarcode()+"_MRC.xml");
                            java.io.File MODSf = new java.io.File(MetadataPath.getAbsolutePath()+"\\"+b.getBarcode()+"_MODS.xml");
                            BufferedReader Dcr= new BufferedReader(new FileReader(DCf));
                            String line = Dcr.readLine();
                            String title = null;
                            while (line!=null)
                            {
                                System.out.println(line);
                                if(line.contains("<dc:title>"))
                                {
                                    int index = line.indexOf('>');
                                    int lindex = line.lastIndexOf("<");
                                    title = line.substring(index+1, lindex-1);
                                    System.out.println("Title is "+title);
                                    break;
                                }
                                line=Dcr.readLine();
                            }
                            String tiffpath = null;
                            if (Language.equals("ara"))
                                tiffpath="z:\\ARA_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\IMAGES";
                            else if(Language.equals("heb"))
                                tiffpath="z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\IMAGES";
                            if(Language.equals("ara"))
                            {
                                Process copy = Runtime.getRuntime().exec("cmd /c copy Z:\\ARA_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_DC.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                copy.waitFor();
                                Process copy1 = Runtime.getRuntime().exec("cmd /c copy Z:\\ARA_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_MRC.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                copy1.waitFor();
                                Process copy2 = Runtime.getRuntime().exec("cmd /c copy Z:\\ARA_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_MODS.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                copy2.waitFor();
                                java.io.File tempfile = new java.io.File("Z:\\ARA_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\ALTO");
                                if(tempfile.exists())
                                {
                                    Process copy3 =  Runtime.getRuntime().exec("cmd /c copy Z:\\ARA_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\ALTO " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                    copy3.waitFor();
                                }
                            }
                            else if(Language.equals("heb"))
                            {
                                Process copy = Runtime.getRuntime().exec("cmd /c copy Z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_DC.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                copy.waitFor();
                                Process copy1 = Runtime.getRuntime().exec("cmd /c copy Z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_MRC.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                copy1.waitFor();
                                Process copy2 = Runtime.getRuntime().exec("cmd /c copy Z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_MODS.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                copy2.waitFor();
                                java.io.File tempfile = new java.io.File("Z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\ALTO");
                                if(tempfile.exists())
                                {
                                    Process copy3 =  Runtime.getRuntime().exec("cmd /c copy Z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\ALTO " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                    copy3.waitFor();
                                }
                            }
                            System.out.println(title);
                            System.out.println("creating RAW metadata");
                            RawJpegMetadata rm = new RawJpegMetadata(DCf,MODSf,MARCf,"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode(),title);
                            Thread t = new Thread(rm);
                            t.start();
                            t.join();
                            System.out.println("creating tiff metadata");
                            TiffJpegMetadata tm = new TiffJpegMetadata(DCf,MODSf,MARCf,"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode(),title,b.getBarcode(),tiffpath);
                            Thread t1 = new Thread(tm);
                            t1.start();
                            t1.join();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Exception in creating RAW-JP2 METS in main thread"+e);
                    }

                    try
                    {
                         boolean success=deleteFile("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJPEG2K\\IMAGES");
                    if(success)
                        System.out.println("IMAGES folder deleted");
                    else
                        System.out.println("IMAGES folder could not be deleted");
                    java.io.File file1 = new java.io.File("D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJPEG2K");
                        java.io.File file2 = new java.io.File("D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_PRESRV_BAG");
                        if(file1.renameTo(file2))
                            System.out.println("RAWJPEG rename successful");
                        else
                            System.out.println("RAWJPEG rename failed");
                        java.io.File file3 = new java.io.File("D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K");
                        java.io.File file4 = new java.io.File("D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_ACCESS_BAG");
                        //Thread.sleep(10000);
                        if(file3.renameTo(file4))
                            System.out.println("TIFFJPEG rename successful");
                        else
                            System.out.println("TIFFJPEG rename failed");
                    }
                    catch(Exception e)
                    {
                        System.out.println("Exception in renaming bags with barcodes"+e);
                    }













                   
                    try
                    {
                        Process theProcess = Runtime.getRuntime().exec("cmd /c C:\\bagit-3.4-bin\\bagit-3.4\\bin\\bag baginplace d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_PRESRV_BAG"  );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                    try
                    {
                        Process theProcess = Runtime.getRuntime().exec("cmd /c C:\\bagit-3.4-bin\\bagit-3.4\\bin\\bag baginplace d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_ACCESS_BAG");
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                        java.io.File bag_dir = new java.io.File("d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode());
                        java.io.File dest_dir= new java.io.File("U:\\assetstore-ro\\"+b.getBarcode());
                        if(!dest_dir.exists())
                            dest_dir.mkdir();
                        Process copyprocess= Runtime.getRuntime().exec("cmd /c xcopy " +bag_dir.getAbsolutePath()+"\\*"+" "+dest_dir.getAbsolutePath()+" /S /i");
                        in = copyprocess.getInputStream();
                        bin = new BufferedReader(new InputStreamReader(in));
                        msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                        /*java.io.File bag_dir = new java.io.File("d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode());
                        java.io.File dest_dir= new java.io.File("U:\\assetstore-ro\\"+b.getBarcode());
                        if(!dest_dir.exists())
                            dest_dir.mkdir();
                        Process copyprocess= Runtime.getRuntime().exec("cmd /c xcopy " +bag_dir.getAbsolutePath()+"\\*"+" "+dest_dir.getAbsolutePath() +" /S /i");
                        in = copyprocess.getInputStream();
                        bin = new BufferedReader(new InputStreamReader(in));
                        msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }*/
                        Tunnel t = new Tunnel();
                        t.go();
                        String barcode="'"+b.getBarcode()+"'";
                        String barcode1 = b.getBarcode()+"'";
                        String pname="'"+p.getProjectname()+"'";
                        String barcode2="'"+b.getBarcode();

                        t.insert(barcode, "'/dspace1/assetstore-ro/'", pname, barcode2+"_PRESRV_BAG'", "'new'","'preservation'");

                        t.done();
                        t=null;
                        Thread.sleep(5000);
                        t = new Tunnel();
                        t.go();
                        t.insert(barcode, "'/dspace1/assetstore-ro/'", pname, barcode2+"_ACCESS_BAG'", "'new'","'access'");
                        t.done();
                        t=null;
                        java.io.File bf=new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode());
                        if(dest_dir.exists())
                        {
                        boolean test= deleteDirectory(bf);
                        if(test)
                            System.out.println(bf.getAbsolutePath()+" is deleted ");
                        else
                            System.out.println(bf.getAbsolutePath()+" is not deleted ");
                        }
                       else
                        {
                            System.out.println(bag_dir.getAbsolutePath()+ " could not be copied to "+dest_dir.getAbsolutePath());
                       }


                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
    
                }
                else
                {
                    java.io.File f=new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJPEG2K\\IMAGES");
                    java.io.File file;
                    if(!f.exists())
                    {
                        boolean raw = (new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RawJPEG2k\\IMAGES").mkdir());
                    }
                    String bookpath="d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RawJPEG2k\\JPEG2K";
                    java.io.File bpath = new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RawJPEG2k\\JPEG2K");
                    if (!bpath.exists())
                    {
                        boolean bj = (new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RawJPEG2k\\JPEG2K").mkdir());
                    }
                    String[] children=f.list();
                    for (int i = 0;i<children.length;i++)
                    {
                        if(children[i].endsWith("cr2"))
                        {
                            file = new java.io.File(f.getAbsolutePath()+"\\"+children[i]);
                            System.out.println(file.getAbsolutePath());
                            pool.assign(new TestWorkerThread(file,i,"RAW",bookpath));
                        }
                    }
                    pool.complete();
                    entityManager.getTransaction().begin();
                    b.setJpc(Boolean.TRUE);
                    entityManager.getTransaction().commit();
                    file=new java.io.File("z:\\OCR_FIN\\cultural_imaginings\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\IMAGES");
                    bookpath="z:\\OCR_FIN\\cultural_imaginings\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\IMAGES";
                    boolean tif = (new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\tiffJPEG2k").mkdir());
                    boolean tifjpeg = (new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\tiffJPEG2k\\JPEG2K").mkdir());
              
                    children = file.list();
                    for (int i = 0;i<children.length;i++)
                    {
                        if(children[i].endsWith("tif"))
                        {
                        file = new java.io.File(bookpath+"\\"+children[i]);
                        System.out.println(file.getAbsolutePath());
                        pool.assign(new TestWorkerThread(file,i,"tiff","d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TiffJpeg2k\\JPEG2k"));
                        }

                    }
                    System.out.println("Tiff conversion is done for roman book"+file.getAbsolutePath());
                    pool.complete();
                    entityManager.getTransaction().begin();
                    b.setJpeg(Boolean.TRUE);
                    entityManager.getTransaction().commit();
                    try
                    {
                        java.io.File test= new java.io.File("d:\\backup\\"+project_id+"\\"+p2+"\\TIFFJPEG2k\\PDF\\");
                        if(!test.exists())
                        {
                            boolean testchk = test.mkdir();
                        }
                      
                        java.io.File f1 = new java.io.File("z:\\OCR_FIN\\cultural_imaginings\\"+project_id+"\\"+p2+"\\PDF\\"+p2+".pdf");
                        java.io.File f2= new java.io.File("z:\\OCR_FIN\\cultural_imaginings\\"+project_id+"\\"+p2+"\\PDF\\HighRes_"+p2+".pdf");
                        String path = "d:\\backup\\"+project_id+"\\"+p2+"\\tiffjpeg2k\\PDF\\LowRes_"+p2+".pdf";
                        System.out.println("PDF file Path is "+f1.getAbsolutePath());
                        System.out.flush();
                        //File tofile = new File("c:\temp");
                        // tofile=new File(tofile,f1.getName());
                        Process theProcess = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Kirtas\\Setup1\\consoleapplication2.exe " +f1.getAbsolutePath() + " " + path );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                        System.out.println("C:\\Program Files (x86)\\Kirtas\\Setup1\\consoleapplication2.exe " +f1.getAbsolutePath() + " " + path );
                        System.out.println("waiting for the process CLcompr");
                        theProcess.waitFor();
                        System.out.println("waiting over for the process CLcompr");
                        entityManager.getTransaction().begin();
                        b.setOCRed(Boolean.TRUE);
                        entityManager.getTransaction().commit();
 
                        java.io.File dir = new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2k\\PDF");

                        if(f1.renameTo(f2))
                            System.out.println(f1.getAbsolutePath()+" rename to "+f2.getAbsolutePath()+" successfull");
                        else
                            System.out.println(f1.getAbsolutePath()+" rename to "+f2.getAbsolutePath()+" failed");
                        Process theProcess1 = Runtime.getRuntime().exec("cmd /c move "+ f2.getAbsolutePath() +" "+ dir.getAbsolutePath());
                    }
                    catch (Exception e)
                    {
                        System.out.println("dcraw failure"+e);
                        e.printStackTrace();
                    }
                    try
                    {
                        System.out.println("creating metadata");
                        java.io.File mfile= new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJPEG2K\\METADATA");
                        if(!mfile.exists())
                            mfile.mkdir();
                        java.io.File mmfile= new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJPEG2K\\METADATA\\MIX");
                        if(!mmfile.exists())
                            mmfile.mkdir();
                        java.io.File tmfile= new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                        if(!tmfile.exists())
                            tmfile.mkdir();
                        java.io.File tmmfile= new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA\\MIX");
                        if(!tmmfile.exists())
                            tmmfile.mkdir();
                        java.io.File MetadataPath = new java.io.File("Z:\\OCR_FIN\\CULTURAL_IMAGININGS\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA");
                        System.out.println("MetadataPath.exists(): "+MetadataPath.exists());
                        if(MetadataPath.exists())
                        {
                            System.out.println("creating objects for DC MARC and MODS Files");
                            java.io.File DCf = new java.io.File(MetadataPath.getAbsolutePath()+"\\"+b.getBarcode()+"_DC.xml");
                            java.io.File MARCf = new java.io.File(MetadataPath.getAbsolutePath()+"\\"+b.getBarcode()+"_MRC.xml");
                            java.io.File MODSf = new java.io.File(MetadataPath.getAbsolutePath()+"\\"+b.getBarcode()+"_MODS.xml");
                            BufferedReader Dcr= new BufferedReader(new FileReader(DCf));
                            String line = Dcr.readLine();
                            String title = null;
                            while (line!=null)
                            {
                                System.out.println(line);
                                if(line.contains("<dc:title>"))
                                {
                                    int index = line.indexOf('>');
                                    int lindex = line.lastIndexOf("<");
                                    title = line.substring(index+1, lindex-1);
                                    System.out.println("Title is "+title);
                                    break;
                                }
                                line=Dcr.readLine();
                            }
                            System.out.println(title);
                            System.out.println("creating RAW metadata");
                            RawJpegMetadata rm = new RawJpegMetadata(DCf,MODSf,MARCf,"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode(),title);
                            Thread t = new Thread(rm);
                            t.start();
                            System.out.println("waiting for RAW thread");
                            t.join();
                            java.io.File pgfile = new java.io.File("Z:\\OCR_FIN\\CULTURAL_IMAGININGS\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\IMAGES\\PageNumbers.tif");
                            if(pgfile.exists())
                                pgfile.delete();
                            java.io.File pxfile = new java.io.File("Z:\\OCR_FIN\\CULTURAL_IMAGININGS\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\IMAGES\\PageNumbers.xml");
                            if(pxfile.exists())
                                pxfile.delete();
                            System.out.println("creating tiff metadata");
                            TiffJpegMetadata tm = new TiffJpegMetadata(DCf,MODSf,MARCf,"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode(),title,b.getBarcode(),"Z:\\OCR_FIN\\CULTURAL_IMAGININGS\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\IMAGES");
                            Thread t1 = new Thread(tm);
                            t1.start();
                            System.out.println("Waiting for Tiff thread");
                            t1.join();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Exception in creating RAW-JP2 METS in main thread"+e);
                    }
                
                 
                    try
                    {
                        if(Language.equals("rom"))
                        {
                            Process copy = Runtime.getRuntime().exec("cmd /c copy Z:\\OCR_FIN\\CULTURAL_IMAGININGS\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_DC.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFPEG2K\\METADATA");
                            copy.waitFor();
                            Process copy1 = Runtime.getRuntime().exec("cmd /c copy Z:\\OCR_FIN\\CULTURAL_IMAGININGS\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_MRC.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                            copy1.waitFor();
                            Process copy2 = Runtime.getRuntime().exec("cmd /c copy Z:\\OCR_FIN\\CULTURAL_IMAGININGS\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\"+b.getBarcode()+"_MODS.xml " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                            copy2.waitFor();
                            java.io.File tempfile = new java.io.File("Z:\\OCR_FIN\\CULTURAL_IMAGININGS\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\ALTO");
                            if(tempfile.exists())
                            {
                                Process copy3 =  Runtime.getRuntime().exec("cmd /c copy Z:\\HEB_FIN\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\METADATA\\ALTO " +"D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K\\METADATA");
                                copy3.waitFor();
                            }
                        }

                        java.io.File file1 = new java.io.File("D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\RAWJPEG2K");
                        java.io.File file2 = new java.io.File("D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_PRESRV_BAG");
                        if(file1.renameTo(file2))
                            System.out.println("RAWJPEG rename successful");
                        else
                            System.out.println("RAWJPEG rename failed");
                        java.io.File file3 = new java.io.File("D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\TIFFJPEG2K");
                        java.io.File file4 = new java.io.File("D:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_ACCESS_BAG");
                        //Thread.sleep(10000);
                        if(file3.renameTo(file4))
                            System.out.println("TIFFJPEG rename successful");
                        else
                            System.out.println("TIFFJPEG rename failed");
                    }
                    catch(Exception e)
                    {
                        System.out.println("Exception in renaming bags with barcodes"+e);
                    }
                    boolean success=deleteFile("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_PRESRV_BAG"+"\\IMAGES");
                    if(success)
                        System.out.println("IMAGES folder deleted");
                    else
                        System.out.println("IMAGES folder could not be deleted");
                    try
                    {
                        System.out.println("creating RAW Bag");
                        Process theProcess = Runtime.getRuntime().exec("cmd /c C:\\bagit-3.4-bin\\bagit-3.4\\bin\\bag baginplace d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_PRESRV_BAG" );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                    try
                    {
                        System.out.println("creating TIFF bag");
                        Process theProcess = Runtime.getRuntime().exec("cmd /c C:\\bagit-3.4-bin\\bagit-3.4\\bin\\bag baginplace d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_ACCESS_BAG" );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                        in=null;
                        bin=null;
                        msg=null;
                        System.out.println("Tiff bag created");

                        java.io.File bag_dir = new java.io.File("d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode());
                        java.io.File dest_dir= new java.io.File("U:\\assetstore-ro\\"+b.getBarcode());
                        if(!dest_dir.exists())
                            dest_dir.mkdir();
                        Process copyprocess= Runtime.getRuntime().exec("cmd /c xcopy " +bag_dir.getAbsolutePath()+"\\*"+" "+dest_dir.getAbsolutePath() +" /S /i");
                        in = copyprocess.getInputStream();
                        bin = new BufferedReader(new InputStreamReader(in));
                        msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                        Tunnel t = new Tunnel();
                        t.go();
                        String barcode="'"+b.getBarcode()+"'";
                        String barcode1 = b.getBarcode()+"'";
                        String pname="'"+p.getProjectname()+"'";
                        String barcode2="'"+b.getBarcode();

                        t.insert(barcode, "'/dspace1/assetstore-ro/'", pname, barcode2+"_PRESRV_BAG'", "'new'","'preservation'");
                        
                        t.done();
                        t=null;
                        Thread.sleep(5000);
                        t = new Tunnel();
                        t.go();
                        t.insert(barcode, "'/dspace1/assetstore-ro/'", pname, barcode2+"_ACCESS_BAG'", "'new'","'access'");
                        t.done();
                        t=null;
                        java.io.File bf=new java.io.File("d:\\backup\\"+p.getProjectname()+"\\"+b.getBarcode());
                        boolean test= deleteDirectory(bf);
                        if(test)
                            System.out.println(bf.getAbsolutePath()+" is deleted ");
                        else
                            System.out.println(bf.getAbsolutePath()+" is not deleted ");


                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
    
                }//end else
            }//end if
        }//end inner for
    }//end outer for
}//end method
    public  boolean deleteFile(String sFilePath)
{
  java.io.File oFile = new java.io.File(sFilePath);
  if(oFile.isDirectory())
  {
    java.io.File[] aFiles = oFile.listFiles();
    for(java.io.File oFileCur: aFiles)
    {
       deleteFile(oFileCur.getAbsolutePath());
    }
  }
  return oFile.delete();
}
    void copyDirectory(java.io.File srcPath, java.io.File dstPath)
                               throws IOException{

  if (srcPath.isDirectory()){

      if (!dstPath.exists()){

        dstPath.mkdir();

     }


      System.out.println("dsetination path is "+dstPath.getAbsolutePath());
     String files[] = srcPath.list();

    for(int i = 0; i < files.length; i++){


        if(files[i].endsWith("tiff") || files[i].endsWith("jpg"))
        {
            continue;
        }
           System.out.println("file in "+ srcPath.getAbsolutePath()+"is"+files[i]);

        copyDirectory(new java.io.File(srcPath, files[i]),
                     new java.io.File(dstPath, files[i]));


      }

    }

   else{

      if(!srcPath.exists()){

        System.out.println("File or directory does not exist.");

       System.exit(0);

      }

else

      {

       InputStream in = new FileInputStream(srcPath);
       OutputStream out = new FileOutputStream(dstPath);
                     // Transfer bytes from in to out

            byte[] buf = new byte[1024];

              int len;

           while ((len = in.read(buf)) > 0) {

          out.write(buf, 0, len);

        }

       in.close();

           out.close();

      }

   }



    }

 boolean deleteDirectory(java.io.File dir)
        {
           try{
            if (dir.isDirectory()) {
        String[] children = dir.list();
        for (int i=0; i<children.length; i++) {
            boolean success = deleteDirectory(new java.io.File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }

    // The directory is now empty so delete it
    return dir.delete();

           }
           catch(Exception e)
           {
               System.out.println(e);
               e.printStackTrace();
           }
            return false;


    }//GEN-LAST:event_jButton1ActionPerformed

 private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
     // TODO add your handling code here:
     java.io.File f=null;
     JFileChooser jFileChooser1= new JFileChooser();
     int returnVal = jFileChooser1.showOpenDialog(menuBar);
        if (returnVal == JFileChooser.APPROVE_OPTION  )
        {
        //get the new Project's Parent folder path and store it in a file object
        f = jFileChooser1.getSelectedFile();
        }
     java.io.File dir=f.getParentFile();
     System.out.println(f.getParentFile());
     System.out.println(dir.getAbsolutePath());
     java.io.File dest=new java.io.File(dir.getAbsolutePath()+"\\optimized_pdf.pdf");
     System.out.println(dest.getAbsolutePath());
     try
     {
     Process theProcess = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Kirtas\\Setup1\\consoleapplication2.exe " +f.getAbsolutePath() + " " + dest.getAbsolutePath() );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                        theProcess.waitFor();
     }
     catch(Exception e)
     {
         System.out.println(e);
         e.printStackTrace();
     }
 }//GEN-LAST:event_jMenuItem1ActionPerformed

 private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
     // TODO add your handling code here:
     java.io.File f=null;
     JFileChooser jFileChooser1= new JFileChooser();
     int returnVal = jFileChooser1.showOpenDialog(menuBar);
        if (returnVal == JFileChooser.APPROVE_OPTION  )
        {
        //get the new Project's Parent folder path and store it in a file object
        f = jFileChooser1.getSelectedFile();
        }
     String[] children = f.list();
     for(int i = 0; i<children.length;i++)
     {
         String barcode = children[i];
         Book b=entityManager.find(Book.class, barcode);
                Project p =b.getProjectid();
      try
                    {
                        System.out.println("creating RAW Bag");
                        Process theProcess = Runtime.getRuntime().exec("cmd /c C:\\bagit-3.4-bin\\bagit-3.4\\bin\\bag baginplace d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_PRESRV_BAG" );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                    try
                    {
                        System.out.println("creating TIFF bag");
                        Process theProcess = Runtime.getRuntime().exec("cmd /c C:\\bagit-3.4-bin\\bagit-3.4\\bin\\bag baginplace d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode()+"\\"+b.getBarcode()+"_ACCESS_BAG" );
                        InputStream in = theProcess.getInputStream();
                        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                        String msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                        in=null;
                        bin=null;
                        msg=null;
                        System.out.println("Tiff bag created");
                        java.io.File bag_dir = new java.io.File("d:\\backup\\" +p.getProjectname()+"\\"+b.getBarcode());
                        java.io.File dest_dir= new java.io.File("U:\\assetstore-ro\\"+b.getBarcode());
                        if(!dest_dir.exists())
                            dest_dir.mkdir();
                        Process copyprocess= Runtime.getRuntime().exec("cmd /c xcopy " +bag_dir.getAbsolutePath()+"\\*"+" "+dest_dir.getAbsolutePath() +" /S /i");
                        in = copyprocess.getInputStream();
                        bin = new BufferedReader(new InputStreamReader(in));
                        msg=bin.readLine();
                        while(bin.readLine()!=null)
                        {
                            System.out.println(msg);
                            msg=bin.readLine();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
     }
 }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private EntityManager entityManager;
    private DefaultTableModel m1,m2;
    private SelectionListener listener;
    private JDialog aboutBox;
}
class SelectionListener implements ListSelectionListener {
    JTable table;
    EntityManager em;
        DefaultTableModel m1,m2;


    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    SelectionListener(JTable table,EntityManager em,DefaultTableModel m1,DefaultTableModel m2) {
        this.table = table;
        this.em=em;
        this.m1=m1;
        this.m2=m2;
    }
    public void valueChanged(ListSelectionEvent e) {
        // If cell selection is enabled, both row and column change events are fired
        int i=0;
        String path;
        //if ((e.getSource() == table.getSelectionModel() || e.getSource() == table.getColumnModel().getSelectionModel())
        		//&& !e.getValueIsAdjusting()
        		//&& table.getSelectedColumn() != table.getSelectedRow()
        		//&& table.getSelectedColumn() != 0
        		//&& table.getSelectedRow() != 0 )
        //{
            int first=table.getSelectedRow();
        int last=table.getSelectedColumn();


        System.out.println("row index: "+ first);
        System.out.println("column index: "+ last);


        if ( first!=-1 && last!=-1)

        {
            i=(Integer)table.getModel().getValueAt(first, last);



        System.out.println("VAlue of i: "+i);
        Project proj=em.find(Project.class, i);
        path=proj.getProjectPath();
        int count=m1.getRowCount() ;
        //clear table1
        while(m2.getRowCount()>0)
            m2.removeRow(0);
        m1.fireTableRowsDeleted(0, count-1);
        /*em.getTransaction().begin();
        Query query = em.createQuery("select p.Project_id,b.Barcode,b.Tiff_end,b.JPC,b.JPEG,b.OCRed from Project p join IMLS.dbo.book b on p.project_id=b.Project_id");
        Iterator results = query.getResultList().iterator();*/


         Query q1=em.createNamedQuery("Book.findMatch");
                        q1.setParameter("projId",proj );
                        Iterator result1 = q1.getResultList().iterator();
        count =0;
        while(result1.hasNext())
        {
            Vector v= new Vector();
            Book b = (Book)result1.next();

            String Barcode=b.getBarcode();
            v.add(Barcode);

            Date tiffd=b.getTiffend();
            if(tiffd!=null)
                v.add(tiffd);
           if(b.getJpc()!=null)
           {
            boolean jpc=b.getJpc();
                  v.add(jpc);
           }
            if(b.getJpeg()!=null)
            {
            boolean jpeg=b.getJpeg();
                v.add(jpeg);
            }
            if(b.getOCRed()!=null )
            {
            boolean ocred=b.getOCRed();
            if(ocred!=false)
            v.add(ocred);
            }
            m2.addRow(v);
            count++;


        }
        m2.fireTableRowsInserted(0, count-1);
        //em.getTransaction().commit();
        }
    }
    //}
}
class ThreadPool {
 /**
  * The threads in the pool.
  */
 protected Thread threads[] = null;
 /**
  * The backlog of assignments, which are waiting
  * for the thread pool.
  */
 Collection assignments = new ArrayList(3);
 /**
  * A Done object that is used to track when the
  * thread pool is done, that is has no more work
  * to perform.
  */
 protected Done done = new Done();

 /**
  * The constructor.
  *
  * @param size  How many threads in the thread pool.
  */
 public ThreadPool(int size)
 {

  threads = new WorkerThread[size];
  for (int i=0;i<threads.length;i++) {
   threads[i] = new WorkerThread(this);
   threads[i].start();

  }
 }

 /**
  * Add a task to the thread pool. Any class
  * which implements the Runnable interface
  * may be assienged. When this task runs, its
  * run method will be called.
  *
  * @param r   An object that implements the Runnable interface
  */
 public synchronized void assign(Runnable r)
 {

  done.workerBegin();
  assignments.add(r);
  notify();
 }

 /**
  * Get a new work assignment.
  *
  * @return A new assignment
  */
 public synchronized Runnable getAssignment()
 {
  try {
   while ( !assignments.iterator().hasNext() )
    wait();

   Runnable r = (Runnable)assignments.iterator().next();
   assignments.remove(r);
   return r;
  } catch (InterruptedException e) {
   done.workerEnd();
   return null;
  }
 }

 /**
  * Called to block the current thread until
  * the thread pool has no more work.
  */
 public void complete()
 {
  done.waitBegin();
  done.waitDone();
 }


 protected void finalize()
 {
  done.reset();
  for (int i=0;i<threads.length;i++) {
   threads[i].interrupt();
   done.workerBegin();
   threads[i].destroy();
  }
  done.waitDone();
 }
}

/**
 * The worker threads that make up the thread pool.
 *
 * @author Jeff Heaton
 * @version 1.0
 */
class WorkerThread extends Thread {
 /**
  * True if this thread is currently processing.
  */
 public boolean busy;
 /**
  * The thread pool that this object belongs to.
  */
 public ThreadPool owner;

 /**
  * The constructor.
  *
  * @param o the thread pool
  */
 WorkerThread(ThreadPool o)
 {
  owner = o;
 }

 /**
  * Scan for and execute tasks.
  */
 public void run()
 {

  Runnable target = null;

  do {
   target = owner.getAssignment();
   if (target!=null) {
    target.run();
    owner.done.workerEnd();
   }
  } while (target!=null);
 }
}
/**
 *
 * This is a thread pool for Java, it is
 * simple to use and gets the job done. This program and
 * all supporting files are distributed under the Limited
 * GNU Public License (LGPL, http://www.gnu.org).
 *
 * This is a very simple object that
 * allows the TheadPool to determine when
 * it is done. This object implements
 * a simple lock that the ThreadPool class
 * can wait on to determine completion.
 * Done is defined as the ThreadPool having
 * no more work to complete.
 *
 * Copyright 2001 by Jeff Heaton
 *
 * @author Jeff Heaton (http://www.jeffheaton.com)
 * @version 1.0
 */
 class Done {

 /**
  * The number of Worker object
  * threads that are currently working
  * on something.
  */
 private int _activeThreads = 0;

 /**
  * This boolean keeps track of if
  * the very first thread has started
  * or not. This prevents this object
  * from falsely reporting that the ThreadPool
  * is done, just because the first thread
  * has not yet started.
  */
 private boolean _started = false;
 /**
  * This method can be called to block
  * the current thread until the ThreadPool
  * is done.
  */

 synchronized public void waitDone()
 {
  try {
   while ( _activeThreads>0 ) {
    wait();
   }
  } catch ( InterruptedException e ) {
  }
 }
 /**
  * Called to wait for the first thread to
  * start. Once this method returns the
  * process has begun.
  */

 synchronized public void waitBegin()
 {
  try {
   while ( !_started ) {
    wait();
   }
  } catch ( InterruptedException e ) {
  }
 }


 /**
  * Called by a Worker object
  * to indicate that it has begun
  * working on a workload.
  */
 synchronized public void workerBegin()
 {

  _activeThreads++;
  _started = true;
  notify();
 }

 /**
  * Called by a Worker object to
  * indicate that it has completed a
  * workload.
  */
 synchronized public void workerEnd()
 {
  _activeThreads--;
  notify();
 }

 /**
  * Called to reset this object to
  * its initial state.
  */
 synchronized public void reset()
 {
  _activeThreads = 0;
 }

}
 class TestWorkerThread implements Runnable
{


 /**
  *
  * @param done
  */
java.io.File f;
int i;
String type,path;
public TestWorkerThread(java.io.File f,int i,String type,String path)
{
    this.f=f;
    this.i=i;
    this.type=type;
    this.path=path;
}

 public void run()
 {

   try {

if(type.equals("RAW"))
{
 java.io.File otf =new java.io.File(path+"\\"+type+i+".jp2");
        String readFormats[] = ImageIO.getReaderMIMETypes();
    String writeFormats[] = ImageIO.getWriterMIMETypes();
    System.out.println("Readers: " +
        Arrays.asList(readFormats));
    System.out.println("Writers: " +
        Arrays.asList(writeFormats));

        System.out.println(f.exists());
            System.out.println(f.getName());

            Iterator readers = ImageIO.getImageReadersByFormatName("cr2");
System.out.println("Number of RAW readers is ");
while(readers.hasNext())    {
    System.out.println(readers.next().toString());

}
readers = ImageIO.getImageReadersByFormatName("cr2");
ImageReader reader = (ImageReader)readers.next();

        //FileSeekableStream iis = new FileSeekableStream(f.getAbsolutePath());
       // RenderedImage image = JAI.create("fileload",args[0]);
        RenderedImage rendimage = ImageIO.read(f);
       ImageWriter writer = null;
        Iterator iter = ImageIO.getImageWritersByFormatName("jpeg2000");
        if (iter.hasNext()) {
            writer = (ImageWriter)iter.next();
        }
        // Prepare output file
        ImageOutputStream ios = ImageIO.createImageOutputStream(otf);
        writer.setOutput(ios);
         // Set the compression quality
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        J2KImageWriteParam jwp = (J2KImageWriteParam)iwp;
//jwp.setLossless(true);
String data[]=jwp.getCompressionTypes();
for (int i = 0;i<data.length;i++)
    System.out.println(data[i]);
jwp.setFilter(jwp.FILTER_53);
//no matter what is set here, the result does not change
jwp.setEncodingRate(Double.MAX_VALUE * 0.5);
jwp.setCompressionMode(jwp.MODE_EXPLICIT);
jwp.setCompressionType(jwp.getCompressionTypes()[0]);
jwp.setCompressionQuality(1);
        // Write the image
        writer.write(null, new IIOImage(rendimage, null, null), jwp);
        // Cleanup
        ios.flush();
        writer.dispose();
        ios.close();
}
else 
{
    System.out.println("in test worker thread for tiff");
    java.io.File otf =new java.io.File(path+"\\"+type+i+".jp2");
        String readFormats[] = ImageIO.getReaderMIMETypes();
    String writeFormats[] = ImageIO.getWriterMIMETypes();
    System.out.println("Readers: " +
        Arrays.asList(readFormats));
    System.out.println("Writers: " +
        Arrays.asList(writeFormats));

        System.out.println(f.exists());
            System.out.println(f.getName());

            Iterator readers = ImageIO.getImageReadersByFormatName("tif");

        ImageReader reader = (ImageReader)readers.next();

        //FileSeekableStream iis = new FileSeekableStream(f.getAbsolutePath());
       // RenderedImage image = JAI.create("fileload",args[0]);
        RenderedImage image = ImageIO.read(f);
      
         System.out.println(reader.getFormatName());
        
        boolean b = ImageIO.write(image,"jpeg2000" , otf);
       System.out.println(b);

}

   }
   catch (Exception e) {
       System.out.println("dcraw failure"+e);
                        }
  }

}

class RawJpegMetadata implements Runnable

{
    java.io.File DCf,MODSf,MARCf;
    String Path,title,barcode; //int i;
    private METS mets = null;
    RenderedImage rawimg,jp2img;
    String Height,Width,make=null,model=null,ISOspeedratings=null,Exifversion=null,ShutterSpeedValue=null,ApertureValue=null,ExposureBiasValue=null,MeteringMode=null,Flash=null,FocalLength=null;
    public RawJpegMetadata(java.io.File DCf,java.io.File MODSf,java.io.File MARCf,String Path,String title)
    {
            System.out.println("RawJpegMetadata constructor");
            this.Path=Path;
            //this.i=i;
            this.DCf=DCf;
            this.MARCf=MARCf;
            this.MODSf=MODSf;
            this.title=title;
            int index=Path.lastIndexOf("\\");
            barcode = Path.substring(index+1);
            //this.rawf=rawf;
            //this.jpegf=jpegf;
            //this.checksum=checksum;
    }
    public void run()
    {
        try
        {
        System.out.println("RawJpegMetadata run method");
        java.io.File Rjpeg,Tjpeg;
        Rjpeg = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES");
        Tjpeg = new java.io.File(Path+"\\RAWJPEG2K\\JPEG2K");

        System.out.println("creating mets wrapper in RAWJPEG2K");
        METSWrapper mw = new METSWrapper();
        mets = mw.getMETSObject();
        System.out.println("created mets");

		mets.setObjID("Example1");
		mets.setProfile("http://localhost/profiles/scientific-datasets-profile");
		mets.setType("investigation");

		MetsHdr mh = mets.newMetsHdr();
                System.out.println("Mets header created");

    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	String currentTime = df.format(cal.getTime());
		mh.setCreateDate(currentTime);
		mh.setLastModDate(currentTime);

		Agent agent = mh.newAgent();
		agent.setRole("CREATOR");
		agent.setType("OTHER");
		agent.setName("GelmanMETSBuild");

		mh.addAgent(agent);

		mets.setMetsHdr(mh);
                System.out.println("METS header added to METS file");

		DmdSec dmd = mets.newDmdSec();
		dmd.setID("MARCDMDsec");
		MdWrap mdw = dmd.newMdWrap();
		mdw.setMDType("MARC");
                mdw.setMIMEType("text/xml");
                mdw.setLabel("MARCxml METADATA");

		mdw.setXmlData(createMARC(MARCf).getDocumentElement());
		dmd.setMdWrap(mdw);
                System.out.println("MARC DMD sec added to METS file");

		mets.addDmdSec(dmd);
                DmdSec dmd1 = mets.newDmdSec();
		dmd1.setID("MODSDMDsec");
		MdWrap mdw1 = dmd.newMdWrap();
		mdw1.setMDType("MODS");
                mdw1.setMIMEType("text/xml");
                mdw1.setLabel("MODS METADATA");

		mdw1.setXmlData(createMODS(MODSf).getDocumentElement());
		dmd1.setMdWrap(mdw1);
                System.out.println("MODS DMD sec added to METS File");

		mets.addDmdSec(dmd1);
                DmdSec dmd2 = mets.newDmdSec();
		dmd2.setID("DCDMDsec");
		MdWrap mdw2 = dmd2.newMdWrap();
		mdw2.setMDType("DC");
                mdw2.setMIMEType("text/xml");
                mdw2.setLabel("Dublin Core METADATA");

		mdw2.setXmlData(createDC(DCf).getDocumentElement());
		dmd2.setMdWrap(mdw2);

                mets.addDmdSec(dmd2);
                System.out.println("DC DMD Sec added to METS File");

                System.out.println(Rjpeg.getAbsolutePath());


        String[] files=Rjpeg.list();
        AmdSec amd = mets.newAmdSec();
                mets.addAmdSec(amd);
                amd.setID("MIXAMDSec");
                System.out.println("MIX AMD Sec added in METS File");
        for(int i=0;i<files.length;i++)
        {
            
            java.io.File rawf = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES\\"+files[i]);
            //System.out.println("creating MIX for FILE in RAWJPEG2K "+rawf.getAbsolutePath());

            if(rawf.getName().equals("Thumbnail")||rawf.getName().equals("Thumbs.db"))
            {
                System.out.println("ignoring file "+rawf.getName());
                continue;
            }
            else
            {
                java.io.File jpegf = new java.io.File(Path+"\\RAWJPEG2K\\JPEG2k\\RAW"+i+".jp2");
                System.out.println("calling Create MIX for File " + jpegf.getAbsolutePath());
            createMIX(jpegf,rawf,i);

                TechMD tmd1 = amd.newTechMD();
                tmd1.setID("MIXPage"+i);
                amd.addTechMD(tmd1);
                MdRef tmd1ref = tmd1.newMdRef();
                tmd1.setMdRef(tmd1ref);
                tmd1ref.setMDType("NISOIMG");
                tmd1ref.setMIMEType("text/xml");
                tmd1ref.setLocType("OTHER");
                tmd1ref.setOtherLocType("PATH");
                tmd1ref.setType("test");
                tmd1ref.setHref(Path+"\\RAWJPEG2K\\METADATA\\MIX\\MIX"+i+".mix");
                tmd1.setMdRef(tmd1ref);
            }
            }
               java.io.File temp =new java.io.File(Path+"\\RAWJPEG2K\\METADATA\\ALTO");
               if(temp.exists())
               {
                   AmdSec amd1 = mets.newAmdSec();
                   mets.addAmdSec(amd1);
                   System.out.println("In ALTO");
                    amd1.setID("ALTOAMDSec");
                   String[] child = temp.list();
                   for( int i = 0; i<temp.length();i++)
                   {

                if(temp.exists())
                {
                TechMD tmda1 = amd1.newTechMD();
                System.out.println("In ALTO");
                tmda1.setID("ALTOPage"+i);
                amd1.addTechMD(tmda1);
                MdRef tmda1ref = tmda1.newMdRef();
                tmda1.setMdRef(tmda1ref);
                tmda1ref.setMDType("OTHER");
                tmda1ref.setOtherMDType("ALTO");
                tmda1ref.setMIMEType("text/xml");
                tmda1ref.setLocType("OTHER");
                tmda1ref.setOtherLocType("PATH");
                tmda1ref.setHref(child[i]);
                       }
                   }
               }
              AmdSec amd2=mets.newAmdSec();
                mets.addAmdSec(amd2);
                RightsMD rmd=amd2.newRightsMD();
                rmd.setID("Rights1");
                amd2.addRightsMD(rmd);
                MdWrap rmdw=rmd.newMdWrap();
                rmdw.setMDType("OTHER");
                rmdw.setOtherMDType("METSRights");
                rmdw.setXmlData(createRMD().getDocumentElement());

                String barcode, project;
                int ind = Path.lastIndexOf("\\");
                barcode = Path.substring(ind+1, Path.length());
                String temp1 = Path.substring(0,ind);
                ind= temp1.lastIndexOf("\\");



                FileSec fs = mets.newFileSec();
                
                
                FileGrp fg1 = fs.newFileGrp();
                fg1.setID("JP2fileGRP");
		fg1.setUse("PageViews");

               for( int i =0;i<files.length;i++)
               {
                    java.io.File rawf = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES\\"+files[i]);
                   if(rawf.getName().equals("Thumbnail")||rawf.getName().equals("Thumbs.db"))
                    continue;

                java.io.File jpegfile = new java.io.File(Path+"\\RAWJPEG2K\\JPEG2k\\RAW"+i+".jp2");
                
                au.edu.apsr.mtk.base.File file2 = fg1.newFile();
		file2.setID("JP2Page"+i);
                if (temp.exists())
                    file2.setAmdID("MIXPage"+i+" ALTOPage"+i);
                else
                    file2.setAmdID("MIXpage"+i);
                file2.setGroupID("Page"+i);
                file2.setSeq(String.valueOf(i));
                file2.setUse("PageViews");
                file2.setMIMEType("image/jp2");
		FLocat floc2 = file2.newFLocat();
                floc2.setLocType("OTHER");
                floc2.setOtherLocType("PATH");
                floc2.setHref(jpegfile.getAbsolutePath());
                file2.addFLocat(floc2);
                fg1.addFile(file2);

            }
                fs.addFileGrp(fg1);
		mets.setFileSec(fs);

                StructMap sm = mets.newStructMap();
                mets.addStructMap(sm);
                sm.setID("PhysicalstructMap");
                sm.setType("Physical");
                sm.setLabel("Physical Structure Map");

                Div d = sm.newDiv();
                d.setDmdID("MARCDMDsec MODSDMDsec DCDMDsec");
                d.setAdmID("Rights1");
		d.setType("Book");
		d.setLabel(title);
		sm.addDiv(d);

                Div d1;
                Fptr fp3;




               for( int i =0;i<files.length;i++)
               {
                   java.io.File rawf = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES\\"+files[i]);
                   if(rawf.getName().equals("Thumbnail")||rawf.getName().equals("Thumbs.db"))
                    continue;
                d1 = d.newDiv();

                if (temp.exists())
                    d1.setDmdID("ALTOPage"+i+" MIXPage"+i);
              
                    d1.setDmdID("MIXPage"+i);
                d1.setType("PAGE");
		d1.setOrder(String.valueOf(i));
		d.addDiv(d1);
                fp3 = d1.newFptr();
		fp3.setFileID("JP2Page"+i);
                d1.addFptr(fp3);
                   }
                StructMap sm1 = mets.newStructMap();
                mets.addStructMap(sm1);
                sm1.setID("LogicalstructMap");
                sm1.setType("Logical");
                sm1.setLabel("Logical Structure Map");

                Div d2 = sm.newDiv();
                d2.setDmdID("MARCDMDsec MODSDMDsec DCDMDsec");
                d2.setAdmID("Rights1");
		d2.setType("Book");
		d2.setLabel(title);
		sm1.addDiv(d2);

                Div d3;
                Fptr fp4;




               for( int i =0;i<files.length;i++)
               {
                   java.io.File rawf = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES\\"+files[i]);
                   if(rawf.getName().equals("Thumbnail")||rawf.getName().equals("Thumbs.db"))
                    continue;
                d3 = d2.newDiv();
                if(temp.exists())
                {
                    System.out.println("in ALTO");
                    d3.setDmdID("ALTOPage"+i+" MIXPage"+i);
                }



                    d3.setDmdID("MIXPage"+i);
                d3.setType("PAGE");
		d3.setOrder(String.valueOf(i));
		d2.addDiv(d3);
                fp4 = d3.newFptr();
		fp4.setFileID("JP2Page"+i);
                d3.addFptr(fp4);

            }
                mw.validate();
                java.io.File file = new java.io.File (Path+"\\RAWJPEG2K\\METADATA\\"+barcode+"_METS.xml");

                PrintStream p = new PrintStream(file);

		mw.write(p);
                p.close();
            }

            
        catch(Exception e)
        {
            System.out.println("Exception in RawJpegMetadata "+e);
            e.printStackTrace();
        }
        finally
        {

        }
    }
    private Document createMODS(java.io.File f) throws ParserConfigurationException
    {
        Document doc=null;
        try{

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        //java.io.File f = new java.io.File("c:\\sample\\32882019068520_MODS.xml");
        doc = builder.parse(f);
        //Element root = doc.createElementNS("http://www.loc.gov/mods/v3", "mods");
        //doc.appendChild(root);

        //Element ti = doc.createElement("titleInfo");
        //Element t = doc.createElement("title");
        //t.setTextContent(title);
        //ti.appendChild(t);
        //root.appendChild(ti);

        //Element g = doc.createElement("genre");
        //g.setTextContent(genre);
        //root.appendChild(g);


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    return doc;
    }
    private Document createMARC(java.io.File f) throws ParserConfigurationException
    {
    Document doc=null;
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        //java.io.File f = new java.io.File("c:\\sample\\32882019068520_MRC.xml");
        doc = builder.parse(f);
        //Element root = doc.createElementNS("http://www.loc.gov/mods/v3", "mods");
        //doc.appendChild(root);

        //Element ti = doc.createElement("titleInfo");
        //Element t = doc.createElement("title");
        //t.setTextContent(title);
        //ti.appendChild(t);
        //root.appendChild(ti);

        //Element g = doc.createElement("genre");
        //g.setTextContent(genre);
        //root.appendChild(g);


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    return doc;
    }
    private Document createDC(java.io.File f) throws ParserConfigurationException
    {
    Document doc=null;
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        //java.io.File f = new java.io.File("c:\\sample\\32882019068520_DC.xml");
        doc = builder.parse(f);
        //Element root = doc.createElementNS("http://www.loc.gov/mods/v3", "mods");
        //doc.appendChild(root);

        //Element ti = doc.createElement("titleInfo");
        //Element t = doc.createElement("title");
        //t.setTextContent(title);
        //ti.appendChild(t);
        //root.appendChild(ti);

        //Element g = doc.createElement("genre");
        //g.setTextContent(genre);
        //root.appendChild(g);


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    return doc;
    }
private void initializeExifData(java.io.File f )
    {
    try
    {
        System.out.println("RAWJPEG2K: in initializeExifData for file "+f.getAbsolutePath());
        Process p = Runtime.getRuntime().exec("C:\\Program Files (x86)\\exiv2\\exiv2 -PEkyct " + f.getAbsolutePath());//+ " > C:\\tempe.txt");
        InputStream in = p.getInputStream();
        //p.waitFor();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        //java.io.File in = new java.io.File("C:\\tempe.txt");
        /*if (!in.exists())
        {
            System.out.println("Exiv file could not be created");
            System.exit(1);
        }*/
        //System.out.println("text file created");
        BufferedReader i = new BufferedReader(new InputStreamReader(in));
        String line = i.readLine();
        System.out.println(line);
        while(line!=null)
        {
            if(line.startsWith("Exif.Image.Make"))
            {
                int ind =line.lastIndexOf("  ");
                //make= "mix:digitalCameraManufacturer";
                String value= line.substring(ind+1,line.length());
                make=value;
                System.out.println(make);
                System.out.println(line);
            }
           if(line.startsWith("Exif.Image.Model"))
            {
                //model="mix:digitalCameraModelName";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                model= value;
                System.out.println(model);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ISOSpeedRatings"))
            {
                //ISOspeedratings="mix:isoSpeedRatings";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                ISOspeedratings=  value;
                System.out.println(ISOspeedratings);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ExifVersion"))
            {
                //Exifversion="mix:exifVersion";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                Exifversion= value;
                System.out.println(Exifversion);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ShutterSpeedValue"))
            {
                //ShutterSpeedValue="mix:shutterSpeedValue";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                ShutterSpeedValue= value;
                System.out.println(ShutterSpeedValue);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ApertureValue"))
            {
                //ApertureValue="mix:apurtureValue";
                int ind =line.lastIndexOf(" ");
                String value=line.substring(ind+1,line.length());
                ApertureValue= value;
                System.out.println(ApertureValue);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ExposureBiasValue"))
            {
                //ExposureBiasValue="mix:exposureBiasValue";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                ExposureBiasValue= value;
                System.out.println(ExposureBiasValue);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.MeteringMode"))
            {
                //MeteringMode="mix:meteringMode";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                MeteringMode= value;
                System.out.println(MeteringMode);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.Flash"))
            {
                //Flash="mix:flash";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                Flash= value;
                System.out.println(Flash);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.FocalLength"))
            {
                //FocalLength="mix:focalLength";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                FocalLength= value;
                System.out.println(FocalLength);
                System.out.println(line);
            }
            line=i.readLine();

        }
        //boolean success=in.delete();
        p.waitFor();
    }
    catch(Exception e)
    {
        System.out.println("exception in initializeExiv"+e);

    }
}
private void loadRawImg(java.io.File rawf)
    {
    try
    {
        System.out.println("in loadRawImg for File "+rawf.getAbsolutePath() );
        Iterator readers = ImageIO.getImageReadersByFormatName("cr2");
        ImageReader reader = (ImageReader)readers.next();
        String readFormats[] = ImageIO.getReaderMIMETypes();
        System.out.println(Arrays.asList(readFormats));
        rawimg= ImageIO.read(rawf);
        System.out.println("Read rawimg "+ rawimg.toString()+ "for file "+ rawf.getAbsolutePath());
        }
    catch(Exception e)
    {
        System.out.println("Exception in loadRawImg"+e);
    }
}
private void loadJp2Img(java.io.File jpegf)
    {
    try
    {
        System.out.println("in loadJp2Img for file "+ jpegf.getAbsolutePath());
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg2000");
        ImageReader reader = (ImageReader)readers.next();
        String readFormats[] = ImageIO.getReaderMIMETypes();
        System.out.println(Arrays.asList(readFormats));
        jp2img= ImageIO.read(jpegf);
        System.out.println("read JP2 img "+ jp2img.toString()+" for file "+ jpegf.getAbsolutePath());
        }
    catch(Exception e)
    {
        System.out.println("Exception in loadRawImg"+e);
    }
}
private void createMets(java.io.File file,java.io.File file1,java.io.File file2,int i,java.io.File jpegf,java.io.File rawf)
    {
    try
    {



    }
    catch(Exception e)
    {
        System.out.println("Exception in createMets"+e);
    }
}
private void createMIX(java.io.File jpegf,java.io.File rawf,int i)
    {
    try
    {
        initializeExifData(rawf);
        System.out.println("in createmix for RAWjpeg2k for file "+ rawf.getAbsolutePath());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element ns = doc.createElementNS("http://www.loc.gov/mix/v20", "mix:mix");
        ns.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        ns.setAttribute( "xsi:schemaLocation", "http://www.loc.gov/standards/mix/mix20/mix20.xsd");
        doc.appendChild(ns);
        System.out.println("createMIX: ns attribute added to MIX file " );
        Element bdit =doc.createElement("mix:BasicDigitalObjectInformation");
        ns.appendChild(bdit);
        Element oid = doc.createElement("mix:ObjectIdentifier");
        bdit.appendChild(oid);
        Element oidt = doc.createElement("mix:objectIdentifierType");
        oidt.appendChild(doc.createTextNode("pathname"));
        oid.appendChild(oidt);
        Element oidv = doc.createElement("mix:objectIdentifierValue");
        System.out.println("jpegf.absolutepath: " +jpegf.getAbsolutePath());
        oidv.appendChild(doc.createTextNode(jpegf.getAbsolutePath()));
        oid.appendChild(oidv);
        Element tfs= doc.createElement("mix:fileSize");
        System.out.println("jpegf.length: "+ jpegf.length());
        tfs.appendChild(doc.createTextNode(String.valueOf(jpegf.length())));
        bdit.appendChild(tfs);
        Element mfdn = doc.createElement("mix:FormatDesignation");
        bdit.appendChild(mfdn);
        Element mfn = doc.createElement("mix:formatName");
        mfn.appendChild(doc.createTextNode("image/jp2"));
        mfdn.appendChild(mfn);
        Element mfv = doc.createElement("mix:formatVersion");
        mfv.appendChild(doc.createTextNode("1.0"));
        mfdn.appendChild(mfv);
        Element mc = doc.createElement("mix:Compression");
        bdit.appendChild(mc);
        Element mcs = doc.createElement("mix:compressionScheme");
        mcs.appendChild(doc.createTextNode("JPEG 2000 Lossless"));
        mc.appendChild(mcs);
        Element mcr = doc.createElement("mix:compressionRatio");
        mc.appendChild(mcr);
        Element mnr = doc.createElement("mix:numerator");
        mnr.appendChild(doc.createTextNode("1"));
        mcr.appendChild(mnr);
        Element mdr = doc.createElement("mix:denominator");
        mdr.appendChild(doc.createTextNode("1"));
        mcr.appendChild(mdr);
        /*Element mfy = doc.createElement("mix:Fixity");
        bdit.appendChild(mfy);
        Element mmda= doc.createElement("mix:messageDigestAlgorithm");
        mmda.appendChild(doc.createTextNode("MD5"));
        mfy.appendChild(mmda);
        Element mmd = doc.createElement("mix:messageDigest");
        System.out.println("RAW createMIX: getting checksum for file "+ jpegf.getAbsolutePath());
        java.io.File tmpfile = new java.io.File(Path+"\\RAWJPEG2K\\manifest-md5.txt");
        BufferedReader tmprdr = new BufferedReader(new FileReader(tmpfile));
        String checksum = null;
        String tmpline = null;
        tmpline=tmprdr.readLine();
        //int modulus;
        while(tmpline!=null)
        {
            
            

            /*if(i>0 && i<10)
                num="00000"+String.valueOf(i);
            else if(i>=10 && i<100 )
                num = "0000"+String.valueOf(i);
            else if(i >=100 && i<1000)
                num = "000"+String.valueOf(i);
            else
                num = "00"+String.valueOf(i);*/

            /*if(tmpline.contains("data/JPEG2K/RAW"+i+".jp2"))
            {

                int ind = tmpline.indexOf(" ");
                checksum = tmpline.substring(0,ind);
                System.out.println("RAW createMIX: tmpline is "+tmpline);
                System.out.println(tmpline.contains("data/JPEG2K/RAW"+i+".jp2"));
                break;
            }
            tmpline = tmprdr.readLine();
        }
        System.out.println("RAW createMIX: checksum for file RAW"+i+".jp2 is "+ checksum );
        mmd.appendChild(doc.createTextNode(checksum));
        mfy.appendChild(mmd);*/
        Element mbii = doc.createElement("mix:BasicImageInformation");
        ns.appendChild(mbii);
        Element mbic = doc.createElement("mix:BasicImageCharacteristics");
        mbii.appendChild(mbic);
        loadJp2Img(jpegf);
        Element miw= doc.createElement("mix:imageWidth");
        System.out.println("RAW CreateMIX: jpegf imgwidth:" +jp2img.getWidth()+" and height: "+jp2img.getHeight());
        miw.appendChild(doc.createTextNode(String.valueOf(jp2img.getWidth())));
        mbic.appendChild(miw);
        Element mih = doc.createElement("mix:imageHeight");
        mih.appendChild(doc.createTextNode(String.valueOf(jp2img.getHeight())));
        mbic.appendChild(mih);
        Element mpi = doc.createElement("mix:PhotometricInterpretation");
        mbic.appendChild(mpi);
        Element mcsi= doc.createElement("mix:colorSpace");
        mcsi.appendChild(doc.createTextNode("sRGB"));
        mpi.appendChild(mcsi);
        Element msfc = doc.createElement("mix:SpecialFormatCharacteristics");
        mbii.appendChild(msfc);
        Element mjp2 = doc.createElement("mix:JPEG2000");
        msfc.appendChild(mjp2);
        Element mcc = doc.createElement("mix:CodecCompliance");
        mjp2.appendChild(mcc);
        Element mcdc = doc.createElement("mix:codec");
        mcdc.appendChild(doc.createTextNode("Java Advanced Imaging API"));
        mcc.appendChild(mcdc);
        Element mcdcv = doc.createElement("mix:codecVersion");
        mcdcv.appendChild(doc.createTextNode("1.1.3"));
        mcc.appendChild(mcdcv);
        Element micmd =doc.createElement("mix:ImageCaptureMetadata");
        ns.appendChild(micmd);
        Element mgpi = doc.createElement("mix:GeneralCaptureInformation");
        micmd.appendChild(mgpi);
        Element mdtc = doc.createElement("mix:dateTimeCreated");
        Process output1 = Runtime.getRuntime().exec("cmd /c dir /TA "+jpegf.getAbsolutePath());
        BufferedReader br1 = new BufferedReader (new InputStreamReader(output1.getInputStream()));
        String out1="";
        String line12 = null;
        int step1=1;
        while((line12 = br1.readLine()) != null )
        {
            System.out.println(line12);
            if(step1==6)
            {
                out1=line12;
            }
            step1++;
        }
        int indx1 = out1.lastIndexOf("M");
        out1=out1.substring(0,indx1+1);
        Date d1 = new Date(out1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        System.out.println("createMIX: date created for file  "+jpegf.getAbsolutePath()+" is " +d1);
        String iso2=sdf.format(d1);
        System.out.println("iso2 date: "+iso2);
        mdtc.appendChild(doc.createTextNode(iso2));
        mgpi.appendChild(mdtc);
        Element mip = doc.createElement("mix:imageProducer");
        mip.appendChild(doc.createTextNode("Cultural Imaginings: The creation of Arab World in the Western Mind. The Gelman Library System, The George Washington University"));
        mgpi.appendChild(mip);
        Element ch1 = doc.createElement("mix:ChangeHistory");
        ns.appendChild(ch1);
        Element mip1 = doc.createElement("mix:ImageProcessing");
        ch1.appendChild(mip1);
        Process output13 = Runtime.getRuntime().exec("cmd /c dir /TA "+rawf.getAbsolutePath());
        System.out.println("cmd /c dir /TA "+rawf.getAbsolutePath());
        BufferedReader br13 = new BufferedReader (new InputStreamReader(output13.getInputStream()));
        String out13="";
        String line13 = null;
        int step13=1;
        while((line13 = br13.readLine()) != null )
        {
            System.out.println(line13);
            if(step13==6)
            {
                out13=line13;
            }
            step13++;
        }

        System.out.println("out13: "+out13);
        int indx13 = out13.lastIndexOf("M");
        System.out.println("INDEX: "+indx13);
        out13=out13.substring(0,indx13+1);
        Date d13 = new Date(out13);
        System.out.println("createMIX: creation date for file "+rawf.getName()+ " is "+ d13);
        String iso1=sdf.format(d13);
        System.out.println("iso1 date: "+iso1);
        Element mdtp1 = doc.createElement("mix:dateTimeProcessed");
        mdtp1.appendChild(doc.createTextNode(iso1));
        mip1.appendChild(mdtp1);
        Element msd = doc.createElement("mix:sourceData");
        msd.appendChild(doc.createTextNode(rawf.getAbsolutePath()));
        mip1.appendChild(msd);
        Element mpa = doc.createElement("mix:processingAgency");
        mpa.appendChild(doc.createTextNode("Gelman Library System, The George Washington University"));
        mip1.appendChild(mpa);
        Element mpr = doc.createElement("mix:processingRationale");
        mpr.appendChild(doc.createTextNode("Canon RAW to JPEG2000 Transformation undertaken for preservation purposes"));
        mip1.appendChild(mpr);
        Element mps = doc.createElement("mix:ProcessingSoftware");
        mip1.appendChild(mps);
        Element mpsn = doc.createElement("mix:processingSoftwareName");
        mpsn.appendChild(doc.createTextNode("Dcraw"));
        mps.appendChild(mpsn);
        Element mpsv = doc.createElement("mix:processingSoftwareVersion");
        mpsv.appendChild(doc.createTextNode("8.99"));
        mps.appendChild(mpsv);
        Element mposn=doc.createElement("mix:processingOperatingSystemName");
        System.out.println("os name "+System.getProperty("os.name"));
        mposn.appendChild(doc.createTextNode(System.getProperty("os.name")));
        mps.appendChild(mposn);
        Element mposv= doc.createElement("mix:processingOperatingSystemVersion");
        System.out.println("OOS version "+System.getProperty("os.version"));
        mposv.appendChild(doc.createTextNode(System.getProperty("os.version")));
        mps.appendChild(mposv);
        Element mpas = doc.createElement("mix:processingActions");
        mpas.appendChild(doc.createTextNode("Format migration from uncompressed Canon RAW to lossless JPEG2000"));
        mip1.appendChild(mpas);
        Element mpimd = doc.createElement("mix:PreviousImageMetadata");
        ch1.appendChild(mpimd);
        Element mix1 = doc.createElement("mix");
        mpimd.appendChild(mix1);
        Element mbdoi = doc.createElement("mix:BasicDigitalObjectInformation");
        mix1.appendChild(mbdoi);
        Element mobi = doc.createElement("mix:ObjectIdentifier");
        mbdoi.appendChild(mobi);
        Element moit = doc.createElement("mix:objectIdentifierType");
        moit.appendChild(doc.createTextNode("pathname"));
        mobi.appendChild(moit);
        Element moiv = doc.createElement("mix:objectIdentifierValue");
        System.out.println("rawf path "+rawf.getAbsolutePath());
        moiv.appendChild(doc.createTextNode(rawf.getAbsolutePath()));
        mobi.appendChild(moiv);
        Element fs = doc.createElement("mix:fileSize");
        System.out.println("rawf size "+rawf.length());
        fs.appendChild(doc.createTextNode(String.valueOf(rawf.length())));
        mbdoi.appendChild(fs);
        Element fd = doc.createElement("mix:formatDesignation");
        mbdoi.appendChild(fd);
        Element fn = doc.createElement("mix:formatName");
        fn.appendChild(doc.createTextNode("image/cr2"));
        fd.appendChild(fn);
        Element fv = doc.createElement("mix:formatVersion");
        fv.appendChild(doc.createTextNode("1.0"));
        fd.appendChild(fv);

        Element bii=doc.createElement("mix:basicImageInformation");
        mix1.appendChild(bii);
        Element bic=doc.createElement("mix:basicImageCharacteristics");
        bii.appendChild(bic);
        loadRawImg(rawf);
        Element iw=doc.createElement("mix:imageWidth");
        System.out.println("createMIX: Raw image width "+ rawimg.getWidth()+" image height: "+rawimg.getHeight());
        iw.appendChild(doc.createTextNode(String.valueOf(rawimg.getWidth())));
        bic.appendChild(iw);
        Element ih= doc.createElement("mix:imageHeight");
        ih.appendChild(doc.createTextNode(String.valueOf(rawimg.getHeight())));
        bii.appendChild(ih);
        Element pi=doc.createElement("mix:PhotometricInterpretation");
        pi.appendChild(doc.createTextNode("RGB"));
        bii.appendChild(pi);
        Element icm =doc.createElement("mix:ImageCaptureMetadata");
        mix1.appendChild(icm);
        Element gci=doc.createElement("mix:GeneralCaptureInformation");
        icm.appendChild(gci);
        Element dtc = doc.createElement("mix:dataTimeCreation");
        System.out.println("iso1 date "+iso1);
        dtc.appendChild(doc.createTextNode(iso1));
        gci.appendChild(dtc);
        Element ip =doc.createElement("mix:imageProducer");
        ip.appendChild(doc.createTextNode("Cultural Imaginings: The creation of Arab World in the Western Mind. The Gelman Library System, The George Washington University"));
        gci.appendChild(ip);
        Element dcc =doc.createElement("mix:DigitalCameraCapture");
        gci.appendChild(dcc);
        Element dcm = doc.createElement("mix:digitalCameraManufacturer");
        System.out.println("createMIX: make: "+make);
        if (make!=null)
        dcm.appendChild(doc.createTextNode(make));
        gci.appendChild(dcm);
        Element dcmd = doc.createElement("mix:digitalCameraModel");
        System.out.println("createMIX: model " +model);
        if(model!=null)
        {
            dcmd.appendChild(doc.createTextNode(model));
        }
        gci.appendChild(dcmd);
        Element ccs=doc.createElement("mix:CameraCaptureSettings");
        gci.appendChild(ccs);
        Element id= doc.createElement("mix:ImageData");
        ccs.appendChild(id);
        Element isr=doc.createElement("mix:isoSpeedRatings");
        System.out.println("createMIX: isoSpeedRatings "+ISOspeedratings);
        if(ISOspeedratings!=null)
            isr.appendChild(doc.createTextNode(ISOspeedratings));
        id.appendChild(isr);
        Element ev =doc.createElement("mix:exifVersion");
        System.out.println("createMIX: exifversion "+Exifversion);
        if(Exifversion!=null)
            ev.appendChild(doc.createTextNode(Exifversion));
        id.appendChild(ev);
        System.out.println("createMIX: shutterSpeedValue "+ShutterSpeedValue);
        Element ssv = doc.createElement("mix:shutterSpeedValue");
        if(ShutterSpeedValue!=null)
            ssv.appendChild(doc.createTextNode(ShutterSpeedValue));
        id.appendChild(ssv);
        System.out.println("createMIX: apertueValue "+ApertureValue);
        Element av = doc.createElement("mix:apertureValue");
        if(ApertureValue!=null)
            av.appendChild(doc.createTextNode(ApertureValue));
        id.appendChild(av);
        System.out.println("createMIX: exposureBiasValue "+ExposureBiasValue);
        Element ebv = doc.createElement("mix:exposureBiasValue");
        if(ExposureBiasValue!=null)
            ebv.appendChild(doc.createTextNode(ExposureBiasValue));
        id.appendChild(ebv);
        System.out.println("createMIX: meteringMode "+ MeteringMode);
        Element mm = doc.createElement("mix:meteringMode");
        if(MeteringMode!=null)
            mm.appendChild(doc.createTextNode(MeteringMode));
        id.appendChild(mm);
        System.out.println("createMIX: Flash "+ Flash);
        Element fl = doc.createElement("mix:flash");
        if(Flash!=null)
            fl.appendChild(doc.createTextNode(Flash));
        id.appendChild(fl);
        System.out.println("createMIX: FocalLength "+ FocalLength);
        Element fol =doc.createElement("mix:focalLength");
        if(FocalLength!=null)
            fol.appendChild(doc.createTextNode(FocalLength));
        id.appendChild(fol);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

//initialize StreamResult with File object to save to file
        System.out.println("Writing MIX data to File "+ Path+"\\RAWJPEG2K\\METADATA\\mix\\RAWmix"+i+".mix");
        java.io.File f = new java.io.File(Path+"\\RAWJPEG2K\\METADATA\\mix\\RAWmix"+i+".xml");
        StreamResult result = new StreamResult(f);
DOMSource source = new DOMSource(doc);
transformer.transform(source, result);

    }
    catch(Exception e)
    {
        System.out.println("Exception in createMIX "+e);
        e.printStackTrace();
    }
}
private Document createRMD() throws ParserConfigurationException
    {
    Document doc=null;
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
        Element root = doc.createElement("rts:RightsDeclarationMD");
        root.setAttribute("xsi:schemaLocation", "http://cosimo.stanford.edu/sdr/metsrights/ http://cosimo.stanford.edu/sdr/metsrights.xsd");
        root.setAttribute("RIGHTSCATEGORY", "PUBLIC DOMAIN");
        doc.appendChild(root);
        Element e1 = doc.createElement("rts:Context");
        e1.setAttribute("CONTEXTCLASS", "GENERAL PUBLIC");
        root.appendChild(e1);
        Element e2 = doc.createElement("rts:Constraints");
        e2.setAttribute("CONSTRAINTTYPE", "RE-USE");
        e1.appendChild(e2);
        Element e3 =doc.createElement("rts:ConstraintDescription");
        e3.appendChild(doc.createTextNode("Most items selected for the Cultural Imaginings project are in the public domain. However, copyrights to some items may be held by creators or their descendants. The Gelman Library at George Washington University respects the interests of copyright holders and encourages them to contact us with questions about participation in this project. Users of these materials are also responsible for compliance with relevant copyright law."));
        e2.appendChild(e3);

        //Element root = doc.createElementNS("http://www.loc.gov/mods/v3", "mods");
        //doc.appendChild(root);

        //Element ti = doc.createElement("titleInfo");
        //Element t = doc.createElement("title");
        //t.setTextContent(title);
        //ti.appendChild(t);
        //root.appendChild(ti);

        //Element g = doc.createElement("genre");
        //g.setTextContent(genre);
        //root.appendChild(g);


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    return doc;
    }

}

class TiffJpegMetadata implements Runnable{
    java.io.File DCf,MODSf,MARCf;
    String Path,title; //int i;
    private METS mets = null;
    RenderedImage rawimg,tiffimg,jp2img;
    String Height,Barcode,Width,make=null,model=null,ISOspeedratings=null,Exifversion=null,ShutterSpeedValue=null,ApertureValue=null,ExposureBiasValue=null,MeteringMode=null,Flash=null,FocalLength=null,tiffpath=null;

    public TiffJpegMetadata(java.io.File DCf,java.io.File MODSf,java.io.File MARCf,String Path,String title,String Barcode,String tiffpath)
    {
            System.out.println("In TiffJpegMetadata constructor");
            this.Path=Path;
            this.tiffpath=tiffpath;
            //this.i=i;
            this.DCf=DCf;
            this.MARCf=MARCf;
            this.MODSf=MODSf;
            this.title=title;
            this.Barcode=Barcode;

    }

    public void run()
    {
        try
        {
            System.out.println("in run method of TiffJpegMetadata");
            java.io.File metafile = new java.io.File(Path+"TiffJPEG2K\\IMAGES");
            if(!metafile.exists())
                metafile.mkdir();
            java.io.File Rjpeg,Tjpeg,Tiff;
            Rjpeg = new java.io.File(Path+"\\RAWJPEG2k\\IMAGES");
            System.out.println("Rjpeg folder "+Rjpeg);
            Tjpeg = new java.io.File(Path+"\\TIFFJPEG2k\\JPEG2K");
            System.out.println("TiffJpeg folder "+ Tjpeg);
            Tiff = new java.io.File(tiffpath);

            METSWrapper mw = new METSWrapper();
            mets = mw.getMETSObject();

		mets.setObjID("Example1");
		mets.setProfile("http://localhost/profiles/scientific-datasets-profile");
		mets.setType("investigation");

		MetsHdr mh = mets.newMetsHdr();

    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	String currentTime = df.format(cal.getTime());
		mh.setCreateDate(currentTime);
		mh.setLastModDate(currentTime);

		Agent agent = mh.newAgent();
		agent.setRole("CREATOR");
		agent.setType("OTHER");
		agent.setName("GelmanMETSBuild");

		mh.addAgent(agent);

		mets.setMetsHdr(mh);

		DmdSec dmd = mets.newDmdSec();
		dmd.setID("MARCDMDsec");
		MdWrap mdw = dmd.newMdWrap();
		mdw.setMDType("MARC");
                mdw.setMIMEType("text/xml");
                mdw.setLabel("MARCxml METADATA");

		mdw.setXmlData(createMARC(MARCf).getDocumentElement());
		dmd.setMdWrap(mdw);

		mets.addDmdSec(dmd);
                DmdSec dmd1 = mets.newDmdSec();
		dmd1.setID("MODSDMDsec");
		MdWrap mdw1 = dmd.newMdWrap();
		mdw1.setMDType("MODS");
                mdw1.setMIMEType("text/xml");
                mdw1.setLabel("MODS METADATA");

		mdw1.setXmlData(createMODS(MODSf).getDocumentElement());
		dmd1.setMdWrap(mdw1);

		mets.addDmdSec(dmd1);
                DmdSec dmd2 = mets.newDmdSec();
		dmd2.setID("DCDMDsec");
		MdWrap mdw2 = dmd2.newMdWrap();
		mdw2.setMDType("DC");
                mdw2.setMIMEType("text/xml");
                mdw2.setLabel("Dublin Core METADATA");

		mdw2.setXmlData(createDC(DCf).getDocumentElement());
		dmd2.setMdWrap(mdw2);
                mets.addDmdSec(dmd2);



        String[] files=Rjpeg.list();
        String[] files1 = Tiff.list();


        AmdSec amd = mets.newAmdSec();
                mets.addAmdSec(amd);
                amd.setID("MIXAMDSec");
        for(int i=0;i<files.length;i++)
        {

            java.io.File rawf = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES\\"+files[i]);


            if(rawf.getName().equals("Thumbnail")||rawf.getName().equals("Thumbs.db"))
            {
                System.out.println("not doing file "+rawf.getName());
                continue;
            }
            else
            {
                java.io.File jpegf = new java.io.File(Path+"\\TIFFJPEG2K\\JPEG2k\\TIFF"+i+".jp2");
                java.io.File tiff = new java.io.File(Tiff+"\\"+files1[i]);
            System.out.println("Raw File "+rawf.getAbsolutePath());
            createMIX(jpegf,rawf,tiff,i);
                System.out.println("creating mix for file in TIFFJPEG2K"+jpegf.getAbsolutePath());
                TechMD tmd1 = amd.newTechMD();
                tmd1.setID("MIXPage"+i);
                amd.addTechMD(tmd1);
                MdRef tmd1ref = tmd1.newMdRef();
                tmd1.setMdRef(tmd1ref);
                tmd1ref.setMDType("NISOIMG");
                tmd1ref.setMIMEType("text/xml");
                tmd1ref.setLocType("OTHER");
                tmd1ref.setOtherLocType("PATH");
                tmd1ref.setType("test");
                tmd1ref.setHref(Path+"\\RAWJPEG2K\\METADATA\\MIX\\MIX"+i+".mix");
                tmd1.setMdRef(tmd1ref);
            }
            }
               java.io.File temp =new java.io.File(Path+"\\RAWJPEG2K\\METADATA\\ALTO");
               if(temp.exists())
               {
                   AmdSec amd1 = mets.newAmdSec();
                   mets.addAmdSec(amd1);
                    amd1.setID("ALTOAMDSec");
                   String[] child = temp.list();
                   for( int i = 0; i<temp.length();i++)
                   {

                System.out.println("creating tmd section for ALTO file "+ child[i]);
                if(temp.exists())
                {
                TechMD tmda1 = amd1.newTechMD();
                tmda1.setID("ALTOPage"+i);
                amd1.addTechMD(tmda1);
                MdRef tmda1ref = tmda1.newMdRef();
                tmda1.setMdRef(tmda1ref);
                tmda1ref.setMDType("OTHER");
                tmda1ref.setOtherMDType("ALTO");
                tmda1ref.setMIMEType("text/xml");
                tmda1ref.setLocType("OTHER");
                tmda1ref.setOtherLocType("PATH");
                tmda1ref.setHref(child[i]);
                       }
                   }
               }
              AmdSec amd2=mets.newAmdSec();
                mets.addAmdSec(amd2);
                RightsMD rmd=amd2.newRightsMD();
                rmd.setID("Rights1");
                amd2.addRightsMD(rmd);
                MdWrap rmdw=rmd.newMdWrap();
                rmdw.setMDType("OTHER");
                rmdw.setOtherMDType("METSRights");
                rmdw.setXmlData(createRMD().getDocumentElement());


                FileSec fs1 = mets.newFileSec();
                FileGrp fg = fs1.newFileGrp();
                fg.setID("PDFfileGRP");
                fg.setUse("FullText");
                au.edu.apsr.mtk.base.File f = fg.newFile();
                f.setID("PDF1");
                f.setUse("HighSpeed");
                f.setMIMEType("application/pdf");
                FLocat floc = f.newFLocat();
                floc.setLocType("OTHER");
                floc.setOtherLocType("PATH");
                floc.setHref("..\\PDf\\"+Barcode+".pdf");
                f.addFLocat(floc);
                fg.addFile(f);
                fg.setUse("FullText");
                au.edu.apsr.mtk.base.File f1 = fg.newFile();
                f1.setID("PDF2");
                f1.setUse("LowSpeed");
                f1.setMIMEType("application/pdf");
                FLocat floc1 = f1.newFLocat();
                floc1.setLocType("OTHER");
                floc1.setOtherLocType("PATH");
                floc1.setHref("..\\PDf\\output.pdf");
                f1.addFLocat(floc1);
                fg.addFile(f1);

                fs1.addFileGrp(fg);
                FileGrp fg1 = fs1.newFileGrp();
                fg1.setID("JP2fileGRP");
		fg1.setUse("PageViews");

               for( int i =0;i<files.length;i++)
               {
                java.io.File rawf = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES\\"+files[i]);
                   if(rawf.getName().equals("Thumbnail")||rawf.getName().equals("Thumbs.db"))
                    continue;
                java.io.File jpegf = new java.io.File(Path+"\\TIFFJPEG2K\\JPEG2k\\RAW"+i+".jp2");
                System.out.println("Creating Filegroup for RAWjp2 file "+files[i]);
                
                au.edu.apsr.mtk.base.File f2 = fg1.newFile();
		f2.setID("JP2Page"+i);
                if(temp.exists())
                    f2.setAmdID("MIXPage"+i+" ALTOPage"+i);
                else
                    f2.setAmdID("MIXPage"+i);

                f2.setGroupID("Page"+i);
                f2.setSeq(String.valueOf(i));
                f2.setUse("PageViews");
                f2.setMIMEType("image/jp2");
		FLocat floc2 = f2.newFLocat();
                floc2.setLocType("OTHER");
                floc2.setOtherLocType("PATH");
                floc2.setHref(jpegf.getAbsolutePath());
                f2.addFLocat(floc2);
                fg1.addFile(f2);

            }
                fs1.addFileGrp(fg1);
		mets.setFileSec(fs1);

                StructMap sm = mets.newStructMap();
                System.out.println("creating Physical structmap");
                mets.addStructMap(sm);
                sm.setID("PhysicalstructMap");
                sm.setType("Physical");
                sm.setLabel("Physical Structure Map");

                Div d = sm.newDiv();
                d.setDmdID("MARCDMDsec MODSDMDsec DCDMDsec");
                d.setAdmID("Rights1");
		d.setType("Book");
		d.setLabel(title);
		sm.addDiv(d);

                Div d1;
                Fptr fp3;




               for( int i =0;i<files.length;i++)
               {
                   java.io.File rawf = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES\\"+files[i]);
                   if(rawf.getName().equals("Thumbnail")||rawf.getName().equals("Thumbs.db"))
                    continue;
                d1 = d.newDiv();
                System.out.println("creating entry for JP2 page "+"JP2Page"+i);
                if(temp.exists())
                    d1.setDmdID("ALTOPage"+i+" MIXPage"+i);
                else
                    d1.setDmdID("MIXPage"+i);
                d1.setType("PAGE");
		d1.setOrder(String.valueOf(i));
		d.addDiv(d1);
                fp3 = d1.newFptr();
		fp3.setFileID("JP2Page"+i);
                d1.addFptr(fp3);
                   }
                StructMap sm1 = mets.newStructMap();
                mets.addStructMap(sm1);
                System.out.println("creating logical structmap");
                sm1.setID("LogicalstructMap");
                sm1.setType("Logical");
                sm1.setLabel("Logical Structure Map");

                Div d2 = sm.newDiv();
                d2.setDmdID("MARCDMDsec MODSDMDsec DCDMDsec");
                d2.setAdmID("Rights1");
		d2.setType("Book");
		d2.setLabel(title);
		sm1.addDiv(d2);

                Div d3;
                Fptr fp4;




               for( int i =0;i<files.length;i++)
               {
                    java.io.File rawf = new java.io.File(Path+"\\RAWJPEG2K\\IMAGES\\"+files[i]);
                   if(rawf.getName().equals("Thumbnail")||rawf.getName().equals("Thumbs.db"))
                    continue;
                d3 = d2.newDiv();
                if(temp.exists())
                    d3.setDmdID("ALTOPage"+i+" MIXPage"+i);
                else
                    d3.setDmdID("MIXPage"+i);
                d3.setType("PAGE");
		d3.setOrder(String.valueOf(i));
		d2.addDiv(d3);
                fp4 = d3.newFptr();
		fp4.setFileID("JP2Page"+i);
                d3.addFptr(fp4);

            }
                mw.validate();
                java.io.File chk = new java.io.File(Path+"\\TIFFJPEG2K\\METADATA");
                if(!chk.exists())
                {
                    boolean success = (new java.io.File(Path+"\\TIFFJPEG2K\\METADATA").mkdir());
                }
               mw.validate();
                java.io.File file = new java.io.File (Path+"\\TIFFJPEG2K\\METADATA\\"+Barcode+"_METS.xml");

                PrintStream p = new PrintStream(file);

		mw.write(p);
                p.close();

                   
            }

            
        catch(Exception e)
        {
            System.out.println("Exception in TiffJpegMetadata "+e);
            e.printStackTrace();
        }
    }
    private Document createMODS(java.io.File f) throws ParserConfigurationException
    {
        Document doc=null;
        try{
            System.out.println("creating MODS for TIFFJPEG2K");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        //java.io.File f = new java.io.File("c:\\sample\\32882019068520_MODS.xml");
        doc = builder.parse(f);
        //Element root = doc.createElementNS("http://www.loc.gov/mods/v3", "mods");
        //doc.appendChild(root);

        //Element ti = doc.createElement("titleInfo");
        //Element t = doc.createElement("title");
        //t.setTextContent(title);
        //ti.appendChild(t);
        //root.appendChild(ti);

        //Element g = doc.createElement("genre");
        //g.setTextContent(genre);
        //root.appendChild(g);


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    return doc;
    }
    private Document createMARC(java.io.File f) throws ParserConfigurationException
    {
    Document doc=null;
        try{

            System.out.println("creating MARC for TIFFJPEG2K");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        //java.io.File f = new java.io.File("c:\\sample\\32882019068520_MRC.xml");
        doc = builder.parse(f);
        //Element root = doc.createElementNS("http://www.loc.gov/mods/v3", "mods");
        //doc.appendChild(root);

        //Element ti = doc.createElement("titleInfo");
        //Element t = doc.createElement("title");
        //t.setTextContent(title);
        //ti.appendChild(t);
        //root.appendChild(ti);

        //Element g = doc.createElement("genre");
        //g.setTextContent(genre);
        //root.appendChild(g);


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    return doc;
    }
    private Document createDC(java.io.File f) throws ParserConfigurationException
    {
    Document doc=null;
        try{
            System.out.println("creating DC for TIFFJPEG2K");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        //java.io.File f = new java.io.File("c:\\sample\\32882019068520_DC.xml");
        doc = builder.parse(f);
        //Element root = doc.createElementNS("http://www.loc.gov/mods/v3", "mods");
        //doc.appendChild(root);

        //Element ti = doc.createElement("titleInfo");
        //Element t = doc.createElement("title");
        //t.setTextContent(title);
        //ti.appendChild(t);
        //root.appendChild(ti);

        //Element g = doc.createElement("genre");
        //g.setTextContent(genre);
        //root.appendChild(g);


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    return doc;
    }
    private void initializeExifData(java.io.File f )
    {
    try
    {
       //Process p = Runtime.getRuntime().exec("cmd /c exiv2 -PEkyct pr " + f.getAbsolutePath()+ " > C:\\tempe.txt");
        //p.waitFor();
        System.out.println("TIFFJPEG2K: initializing exif data");
        System.out.println("TIFFJPEG2K: in initializeExifData for file "+f.getAbsolutePath());
        Process p = Runtime.getRuntime().exec("C:\\Program Files (x86)\\exiv2\\exiv2 -PEkyct " + f.getAbsolutePath());//+ " > C:\\tempe.txt");
        InputStream in = p.getInputStream();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        //java.io.File in = new java.io.File("C:\\tempe.txt");
        //if (!in.exists())
       // {
          //  System.out.println("Exiv file could not be created");
           // System.exit(1);
        //}
        System.out.println("text file created");
        BufferedReader i = new BufferedReader(new InputStreamReader(in));
        String line = i.readLine();
        System.out.println(line);
        while(line!=null)
        {
            if(line.startsWith("Exif.Image.Make"))
            {
                int ind =line.lastIndexOf("  ");
                //make= "mix:digitalCameraManufacturer";
                String value= line.substring(ind+1,line.length());
                make=value;
                System.out.println("MAKE "+make);
                System.out.println(line);
            }
           if(line.startsWith("Exif.Image.Model"))
            {
                //model="mix:digitalCameraModelName";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                model= value;
                System.out.println("MODEL "+model);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ISOSpeedRatings"))
            {
                //ISOspeedratings="mix:isoSpeedRatings";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                ISOspeedratings=  value;
                System.out.println("ISOSPEED "+ISOspeedratings);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ExifVersion"))
            {
                //Exifversion="mix:exifVersion";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                Exifversion= value;
                System.out.println("ExifVersion: "+Exifversion);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ShutterSpeedValue"))
            {
                //ShutterSpeedValue="mix:shutterSpeedValue";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                ShutterSpeedValue= value;
                System.out.println("ShutterSPeed "+ShutterSpeedValue);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ApertureValue"))
            {
                //ApertureValue="mix:apurtureValue";
                int ind =line.lastIndexOf(" ");
                String value=line.substring(ind+1,line.length());
                ApertureValue= value;
                System.out.println("ApertureValue "+ApertureValue);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.ExposureBiasValue"))
            {
                //ExposureBiasValue="mix:exposureBiasValue";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                ExposureBiasValue= value;
                System.out.println("ExposureBiasValue "+ExposureBiasValue);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.MeteringMode"))
            {
                //MeteringMode="mix:meteringMode";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                MeteringMode= value;
                System.out.println("MeteringMode "+MeteringMode);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.Flash"))
            {
                //Flash="mix:flash";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                Flash= value;
                System.out.println("Flash"+Flash);
                System.out.println(line);
            }
            if(line.startsWith("Exif.Photo.FocalLength"))
            {
                //FocalLength="mix:focalLength";
                int ind =line.lastIndexOf("  ");
                String value=line.substring(ind+1,line.length());
                FocalLength= value;
                System.out.println("FocalLength "+FocalLength);
                System.out.println(line);
            }
            line=i.readLine();

        }
       //Boolean success= in.delete();
    }
    catch(Exception e)
    {
        System.out.println("exception in initializeExiv"+e);

    }
}
    private Document createRMD() throws ParserConfigurationException
    {
    Document doc=null;
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
        Element root = doc.createElement("rts:RightsDeclarationMD");
        root.setAttribute("xsi:schemaLocation", "http://cosimo.stanford.edu/sdr/metsrights/ http://cosimo.stanford.edu/sdr/metsrights.xsd");
        root.setAttribute("RIGHTSCATEGORY", "PUBLIC DOMAIN");
        doc.appendChild(root);
        Element e1 = doc.createElement("rts:Context");
        e1.setAttribute("CONTEXTCLASS", "GENERAL PUBLIC");
        root.appendChild(e1);
        Element e2 = doc.createElement("rts:Constraints");
        e2.setAttribute("CONSTRAINTTYPE", "RE-USE");
        e1.appendChild(e2);
        Element e3 =doc.createElement("rts:ConstraintDescription");
        e3.appendChild(doc.createTextNode("Most items selected for the Cultural Imaginings project are in the public domain. However, copyrights to some items may be held by creators or their descendants. The Gelman Library at George Washington University respects the interests of copyright holders and encourages them to contact us with questions about participation in this project. Users of these materials are also responsible for compliance with relevant copyright law."));
        e2.appendChild(e3);

        //Element root = doc.createElementNS("http://www.loc.gov/mods/v3", "mods");
        //doc.appendChild(root);

        //Element ti = doc.createElement("titleInfo");
        //Element t = doc.createElement("title");
        //t.setTextContent(title);
        //ti.appendChild(t);
        //root.appendChild(ti);

        //Element g = doc.createElement("genre");
        //g.setTextContent(genre);
        //root.appendChild(g);


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    return doc;
    }
private void createMIX(java.io.File jpegf,java.io.File rawf,java.io.File Tiff,int i)
    {
    try
    {

        initializeExifData(rawf);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element ns = doc.createElementNS("http://www.loc.gov/mix/v20", "mix:mix");
        ns.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        ns.setAttribute( "xsi:schemaLocation", "http://www.loc.gov/standards/mix/mix20/mix20.xsd");
        doc.appendChild(ns);
        Element bdit =doc.createElement("mix:BasicDigitalObjectInformation");
        ns.appendChild(bdit);
        Element oid = doc.createElement("mix:ObjectIdentifier");
        bdit.appendChild(oid);
        Element oidt = doc.createElement("mix:objectIdentifierType");
        oidt.appendChild(doc.createTextNode("pathname"));
        oid.appendChild(oidt);
        Element oidv = doc.createElement("mix:objectIdentifierValue");
        System.out.println("jpegf Path "+jpegf.getAbsolutePath());
        oidv.appendChild(doc.createTextNode(jpegf.getAbsolutePath()));
        oid.appendChild(oidv);
        Element tfs= doc.createElement("mix:fileSize");
        System.out.println("jpegf size "+jpegf.length());
        tfs.appendChild(doc.createTextNode(String.valueOf(jpegf.length())));
        bdit.appendChild(tfs);
        Element mfdn = doc.createElement("mix:FormatDesignation");
        bdit.appendChild(mfdn);
        Element mfn = doc.createElement("mix:formatName");
        mfn.appendChild(doc.createTextNode("image/jp2"));
        mfdn.appendChild(mfn);
        Element mfv = doc.createElement("mix:formatVersion");
        mfv.appendChild(doc.createTextNode("1.0"));
        mfdn.appendChild(mfv);
        Element mc = doc.createElement("mix:Compression");
        bdit.appendChild(mc);
        Element mcs = doc.createElement("mix:compressionScheme");
        mcs.appendChild(doc.createTextNode("JPEG 2000 Lossless"));
        mc.appendChild(mcs);
        Element mcr = doc.createElement("mix:compressionRatio");
        mc.appendChild(mcr);
        Element mnr = doc.createElement("mix:numerator");
        mnr.appendChild(doc.createTextNode("1"));
        mcr.appendChild(mnr);
        Element mdr = doc.createElement("mix:denominator");
        mdr.appendChild(doc.createTextNode("1"));
        mcr.appendChild(mdr);
        /*Element mfy = doc.createElement("mix:Fixity");
        bdit.appendChild(mfy);
        Element mmda= doc.createElement("mix:messageDigestAlgorithm");
        mmda.appendChild(doc.createTextNode("MD5"));
        mfy.appendChild(mmda);
        Element mmd = doc.createElement("mix:messageDigest");
        java.io.File tmpfile = new java.io.File(Path+"\\TIFFJPEG2K\\manifest-md5.txt");
        BufferedReader tmprdr = new BufferedReader(new FileReader(tmpfile));
        String checksum = null;
        String tmpline = null;
        tmpline=tmprdr.readLine();
        //int modulus;
        while(tmpline!=null)
        {
            String num= null;


            /*if(i>0 && i<10)
                num="00000"+String.valueOf(i);
            else if(i>=10 && i<100 )
                num = "0000"+String.valueOf(i);
            else if(i >=100 && i<1000)
                num = "000"+String.valueOf(i);
            else
                num = "00"+String.valueOf(i);*/

            /*if(tmpline.endsWith(Path+"\\data\\JPEG2K\\Tiff"+i+".jp2"))
            {
                int ind = tmpline.indexOf(" ");
                checksum = tmpline.substring(0,ind);
            }
        }
        mmd.appendChild(doc.createTextNode(checksum));
        mfy.appendChild(mmd);*/
        Element mbii = doc.createElement("mix:BasicImageInformation");
        ns.appendChild(mbii);
        Element mbic = doc.createElement("mix:BasicImageCharacteristics");
        mbii.appendChild(mbic);
        Element miw= doc.createElement("mix:imageWidth");
        loadJp2Img(jpegf);
        System.out.println("jp2img width "+jp2img.getWidth());
        miw.appendChild(doc.createTextNode(String.valueOf(jp2img.getWidth())));
        mbic.appendChild(miw);
        Element mih = doc.createElement("mix:imageHeight");
        System.out.println("jp2img height "+jp2img.getHeight());
        mih.appendChild(doc.createTextNode(String.valueOf(jp2img.getHeight())));
        mbic.appendChild(mih);
        Element mpi = doc.createElement("mix:PhotometricInterpretation");
        mbic.appendChild(mpi);
        Element mcsi= doc.createElement("mix:colorSpace");
        mcsi.appendChild(doc.createTextNode("sRGB"));
        mpi.appendChild(mcsi);
        Element msfc = doc.createElement("mix:SpecialFormatCharacteristics");
        mbii.appendChild(msfc);
        Element mjp2 = doc.createElement("mix:JPEG2000");
        msfc.appendChild(mjp2);
        Element mcc = doc.createElement("mix:CodecCompliance");
        mjp2.appendChild(mcc);
        Element mcdc = doc.createElement("mix:codec");
        mcdc.appendChild(doc.createTextNode("Java Advanced Imaging API"));
        mcc.appendChild(mcdc);
        Element mcdcv = doc.createElement("mix:codecVersion");
        mcdcv.appendChild(doc.createTextNode("1.1.3"));
        mcc.appendChild(mcdcv);
        Element micmd =doc.createElement("mix:ImageCaptureMetadata");
        ns.appendChild(micmd);
        Element mgpi = doc.createElement("mix:GeneralCaptureInformation");
        micmd.appendChild(mgpi);
        Element mdtc = doc.createElement("mix:dateTimeCreated");
        Process output1 = Runtime.getRuntime().exec("cmd /c dir /TA "+jpegf.getAbsolutePath());
        BufferedReader br1 = new BufferedReader (new InputStreamReader(output1.getInputStream()));
        String out1="";
        String line12 = null;
        int step1=1;
        while((line12 = br1.readLine()) != null )
        {
            System.out.println(line12);
            if(step1==6)
            {
                out1=line12;
            }
            step1++;
        }
        System.out.println("out1 " +out1);
        int indx1 = out1.lastIndexOf("M");
        out1=out1.substring(0,indx1+1);
        Date d1 = new Date(out1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String iso2=sdf.format(d1);
        System.out.println("iso2 date "+iso2);
        mdtc.appendChild(doc.createTextNode(iso2));
        mgpi.appendChild(mdtc);
        Element mip = doc.createElement("mix:imageProducer");
        mip.appendChild(doc.createTextNode("Cultural Imaginings: The creation of Arab World in the Western Mind. The Gelman Library System, The George Washington University"));
        mgpi.appendChild(mip);
        Element ch2 = doc.createElement("mix:ChangeHistory");
        ns.appendChild(ch2);
        Element mip1 = doc.createElement("mix:ImageProcessing");
        ch2.appendChild(mip1);
        Element mdtp1 = doc.createElement("mix:dateTimeProcessed");
        Process output13 = Runtime.getRuntime().exec("cmd /c dir /TA "+Tiff.getAbsolutePath());
        BufferedReader br13 = new BufferedReader (new InputStreamReader(output13.getInputStream()));
        String out13="";
        String line13 = null;
        int step13=1;
        while((line13 = br13.readLine()) != null )
        {
            System.out.println(line13);
            if(step13==6)
            {
                out13=line13;
            }
            step13++;
        }

        System.out.println("out13: "+out13);
        int indx13 = out13.lastIndexOf("M");
        System.out.println("INDEX: "+indx13);
        out13=out13.substring(0,indx13+1);
        Date d13 = new Date(out13);
        String iso1=sdf.format(d13);
        System.out.println("iso1 date "+iso1);
        mdtp1.appendChild(doc.createTextNode(iso1));
        mip1.appendChild(mdtp1);
        Element msd = doc.createElement("mix:sourceData");
        msd.appendChild(doc.createTextNode(Tiff.getAbsolutePath()));
        mip1.appendChild(msd);
        Element mpa = doc.createElement("mix:processingAgency");
        mpa.appendChild(doc.createTextNode("Gelman Library System, The George Washington University"));
        mip1.appendChild(mpa);
        Element mpr = doc.createElement("mix:processingRationale");
        mpr.appendChild(doc.createTextNode("TIFF to JPEG2000 Transformation undertaken for preservation purposes"));
        mip1.appendChild(mpr);
        Element mps = doc.createElement("mix:ProcessingSoftware");
        mip1.appendChild(mps);
        Element mpsn = doc.createElement("mix:processingSoftwareName");
        mpsn.appendChild(doc.createTextNode("Java Advanced Imaging API"));
        mps.appendChild(mpsn);
        Element mpsv = doc.createElement("mix:processingSoftwareVersion");
        mpsv.appendChild(doc.createTextNode("1.1.3"));
        mps.appendChild(mpsv);
        Element mposn=doc.createElement("mix:processingOperatingSystemName");
        mposn.appendChild(doc.createTextNode("Windows"));
        mps.appendChild(mposn);
        Element mposv= doc.createElement("mix:processingOperatingSystemVersion");
        mposv.appendChild(doc.createTextNode("5.0"));
        mps.appendChild(mposv);
        Element mpas = doc.createElement("mix:processingActions");
        mpas.appendChild(doc.createTextNode("Format migration from uncompressed TIFF to lossless JPEG2000"));
        mip1.appendChild(mpas);
        Element mpimd = doc.createElement("mix:PreviousImageMetadata");
        ch2.appendChild(mpimd);
        Element mix1 = doc.createElement("mix");
        mpimd.appendChild(mix1);
        Element mbdoi = doc.createElement("mix:BasicDigitalObjectInformation");
        mix1.appendChild(mbdoi);
        Element mobi = doc.createElement("mix:ObjectIdentifier");
        mbdoi.appendChild(mobi);
        Element moit = doc.createElement("mix:objectIdentifierType");
        moit.appendChild(doc.createTextNode("pathname"));
        mobi.appendChild(moit);
        Element moiv = doc.createElement("mix:objectIdentifierValue");
        System.out.println("Tiff PAth"+ Tiff.getAbsolutePath());
        moiv.appendChild(doc.createTextNode(Tiff.getAbsolutePath()));
        mobi.appendChild(moiv);
        Element mfsize = doc.createElement("mix:fileSize");
        System.out.println("Tiff size "+Tiff.length());
        mfsize.appendChild(doc.createTextNode(String.valueOf(Tiff.length())));
        mbdoi.appendChild(mfsize);
        Element mfdn1 = doc.createElement("mix:FormatDesignation");
        mbdoi.appendChild(mfdn1);
        Element mfname =doc.createElement("mix:formatName");
        mfname.appendChild(doc.createTextNode("image/tif"));
        mfdn1.appendChild(mfname);
        Element mfversion =doc.createElement("mix:formatVersion");
        mfversion.appendChild(doc.createTextNode("1.0"));
        mfdn1.appendChild(mfversion);
        Element mcn = doc.createElement("mix:Compression");
        mbdoi.appendChild(mcn);
        Element mcscheme =doc.createElement("mix:compressionScheme");
        mcscheme.appendChild(doc.createTextNode("Uncompressed TIFF"));
        mcn.appendChild(mcscheme);
        Element mcrat = doc.createElement("mix:compressionRatio");
        mcn.appendChild(mcrat);
        Element mnum = doc.createElement("mix:numerator");
        mnum.appendChild(doc.createTextNode("1"));
        mcrat.appendChild(mnum);
        Element mdem = doc.createElement("mix:denominator");
        mdem.appendChild(doc.createTextNode("1"));
        mcrat.appendChild(mdem);
        /*Element mfixity = doc.createElement("mix:Fixity");
        mbdoi.appendChild(mfixity);
        Element mdal = doc.createElement("mix:messageDigestAlgorithm");
        mdal.appendChild(doc.createTextNode("md5"));
        mfixity.appendChild(mdal);
        Element mmdt = doc.createElement("mix:messageDigest");
        java.io.File tmpfile1 = new java.io.File(Path+"\\TIFFJPEG2K\\manifest-md5.txt");
        BufferedReader tmprdr1 = new BufferedReader(new FileReader(tmpfile));
        String checksum1 = null;
        String tmpline1 = null;
        tmpline1=tmprdr1.readLine();
        //int modulus;
        while(tmpline1!=null)
        {
            String num= null;


            if(i>0 && i<10)
                num="00000"+String.valueOf(i);
            else if(i>=10 && i<100 )
                num = "0000"+String.valueOf(i);
            else if(i >=100 && i<1000)
                num = "000"+String.valueOf(i);
            else
                num = "00"+String.valueOf(i);

            if(tmpline.endsWith(Path+"\\TIFFJPEG2K\\data\\IMAGES\\"+Barcode+"_"+num+".tif"))
            {
                int ind = tmpline.indexOf(" ");
                checksum1 = tmpline.substring(0,ind);
            }
        }
        mmdt.appendChild(doc.createTextNode(checksum1));
        mfixity.appendChild(mmdt);*/
        Element mbiin =doc.createElement("mix:BasicImageInformation");
        mix1.appendChild(mbiin);
        Element mbich = doc.createElement("mix:BasicImageCharacteristics");
        mbiin.appendChild(mbich);
        Element mimw = doc.createElement("mix:imageWidth");
        loadTifImg(Tiff);
        System.out.println("tiffimg width "+tiffimg.getWidth());
        mimw.appendChild(doc.createTextNode(String.valueOf(tiffimg.getWidth())));
        mbich.appendChild(mimw);
        Element mimh = doc.createElement("mix:imageHeight");
        System.out.println("tiffimg height "+tiffimg.getHeight());
        mimh.appendChild(doc.createTextNode(String.valueOf(tiffimg.getHeight())));
        mbich.appendChild(mimh);
        Element mpip = doc.createElement("mix:PhotometricInterpretation");
        mbich.appendChild(mpip);
        Element mcsp = doc.createElement("mix:colorSpace");
        mcsp.appendChild(doc.createTextNode("sRGB"));
        mpip.appendChild(mcsp);
        Element mcimd =doc.createElement("mix:CaptureImageMetadata");
        mix1.appendChild(mcimd);
        Element mgcin = doc.createElement("mix:GeneralCaptureMetadata");
        mcimd.appendChild(mgcin);
        Element mdtc1 = doc.createElement("mix:dateTimeCreated");
        System.out.println("iso1 date "+iso1);
        mdtc1.appendChild(doc.createTextNode(iso1));
        mgcin.appendChild(mdtc1);
        Element mimp = doc.createElement("mix:imageProducer");
        mimp.appendChild(doc.createTextNode("Cultural Imaginings: The creation of Arab World in the Western Mind. The Gelman Library System, The George Washington University"));
        mgcin.appendChild(mimp);
        Element ch1 = doc.createElement("mix:ChangeHistory");
        mix1.appendChild(ch1);
        Element mip11 = doc.createElement("mix:ImageProcessing");
        ch1.appendChild(mip11);
        Process output14 = Runtime.getRuntime().exec("cmd /c dir /TA "+rawf.getAbsolutePath());
        BufferedReader br14 = new BufferedReader (new InputStreamReader(output14.getInputStream()));
        String out14="";
        String line14 = null;
        int step14=1;
        while((line14 = br14.readLine()) != null )
        {
            System.out.println(line14);
            if(step14==6)
            {
                out14=line14;
            }
            step14++;
        }

        int indx14 = out14.lastIndexOf("M");
        System.out.println("INDEX: "+indx14);
        out14=out14.substring(0,indx14+1);
        System.out.println("out14 "+out14);
        Date d14 = new Date(out14);
        String iso11=sdf.format(d14);
        System.out.println("iso11 date "+iso11);
        Element mdtp11 = doc.createElement("mix:dateTimeProcessed");
        mdtp1.appendChild(doc.createTextNode(iso11));
        mip11.appendChild(mdtp11);
        Element msd1 = doc.createElement("mix:sourceData");
        System.out.println("rawf Path "+rawf.getAbsolutePath());
        msd1.appendChild(doc.createTextNode(rawf.getAbsolutePath()));
        mip11.appendChild(msd1);
        Element mpa1 = doc.createElement("mix:processingAgency");
        mpa1.appendChild(doc.createTextNode("Gelman Library System, The George Washington University"));
        mip11.appendChild(mpa1);
        Element mpr1 = doc.createElement("mix:processingRationale");
        mpr1.appendChild(doc.createTextNode("Canon RAW to JPEG2000 Transformation undertaken for preservation purposes"));
        mip11.appendChild(mpr1);
        Element mps1 = doc.createElement("mix:ProcessingSoftware");
        mip11.appendChild(mps1);
        Element mpsn1 = doc.createElement("mix:processingSoftwareName");
        mpsn1.appendChild(doc.createTextNode("Dcraw"));
        mps1.appendChild(mpsn1);
        Element mpsv1 = doc.createElement("mix:processingSoftwareVersion");
        mpsv1.appendChild(doc.createTextNode("8.99"));
        mps1.appendChild(mpsv1);
        Element mposn1=doc.createElement("mix:processingOperatingSystemName");
        System.out.println("os name "+System.getProperty("os.name"));
        mposn1.appendChild(doc.createTextNode(System.getProperty("os.name")));
        mps1.appendChild(mposn1);
        Element mposv1= doc.createElement("mix:processingOperatingSystemVersion");
        System.out.println("os version "+System.getProperty("os.version"));
        mposv1.appendChild(doc.createTextNode(System.getProperty("os.version")));
        mps1.appendChild(mposv1);
        Element mpas1 = doc.createElement("mix:processingActions");
        mpas1.appendChild(doc.createTextNode("Format migration from uncompressed Canon RAW to lossless JPEG2000"));
        mip11.appendChild(mpas1);
        Element mpimd1 = doc.createElement("mix:PreviousImageMetadata");
        ch2.appendChild(mpimd1);
        Element mix11 = doc.createElement("mix");
        mpimd1.appendChild(mix11);
        Element mbdoi1 = doc.createElement("mix:BasicDigitalObjectInformation");
        mix11.appendChild(mbdoi1);
        Element mobi1 = doc.createElement("mix:ObjectIdentifier");
        mbdoi1.appendChild(mobi1);
        Element moit1 = doc.createElement("mix:objectIdentifierType");
        moit1.appendChild(doc.createTextNode("pathname"));
        mobi1.appendChild(moit1);
        Element moiv1 = doc.createElement("mix:objectIdentifierValue");
        System.out.println("rawf path "+rawf.getAbsolutePath());
        moiv1.appendChild(doc.createTextNode(rawf.getAbsolutePath()));
        mobi1.appendChild(moiv1);
        Element fs = doc.createElement("mix:fileSize");
        System.out.println("rawf size "+rawf.length());
        fs.appendChild(doc.createTextNode(String.valueOf(rawf.length())));
        mbdoi1.appendChild(fs);
        Element fd = doc.createElement("mix:formatDesignation");
        mbdoi1.appendChild(fd);
        Element fn = doc.createElement("mix:formatName");
        fn.appendChild(doc.createTextNode("image/tif"));
        fd.appendChild(fn);
        Element fv = doc.createElement("mix:formatVersion");
        fv.appendChild(doc.createTextNode("1.0"));
        fd.appendChild(fv);

        Element bii=doc.createElement("mix:basicImageInformation");
        mix11.appendChild(bii);
        Element bic=doc.createElement("mix:basicImageCharacteristics");
        bii.appendChild(bic);
        loadRawImg(rawf);
        Element iw=doc.createElement("mix:imageWidth");
        System.out.println("raw width "+rawimg.getWidth());
        iw.appendChild(doc.createTextNode(String.valueOf(rawimg.getWidth())));
        bic.appendChild(iw);
        Element ih= doc.createElement("mix:imageHeight");
        System.out.println("raw height "+rawimg.getHeight());
        ih.appendChild(doc.createTextNode(String.valueOf(rawimg.getHeight())));
        bii.appendChild(ih);
        Element pi=doc.createElement("mix:PhotometricInterpretation");
        pi.appendChild(doc.createTextNode("RGB"));
        bii.appendChild(pi);
        Element icm =doc.createElement("mix:ImageCaptureMetadata");
        mix1.appendChild(icm);
        Element gci=doc.createElement("mix:GeneralCaptureInformation");
        icm.appendChild(gci);
        Element dtc = doc.createElement("mix:dataTimeCreation");
        System.out.println("iso1 date "+iso1);
        dtc.appendChild(doc.createTextNode(iso1));
        gci.appendChild(dtc);
        Element ip =doc.createElement("mix:imageProducer");
        ip.appendChild(doc.createTextNode("Cultural Imaginings: The creation of Arab World in the Western Mind. The Gelman Library System, The George Washington University"));
        gci.appendChild(ip);
        Element dcc =doc.createElement("mix:DigitalCameraCapture");
        gci.appendChild(dcc);
        Element dcm = doc.createElement("mix:digitalCameraManufacturer");
        if (make!=null)
        dcm.appendChild(doc.createTextNode(make));
        gci.appendChild(dcm);
        Element dcmd = doc.createElement("mix:digitalCameraModel");
        if(model!=null)
        {
            dcmd.appendChild(doc.createTextNode(model));
        }
        gci.appendChild(dcmd);
        Element ccs=doc.createElement("mix:CameraCaptureSettings");
        gci.appendChild(ccs);
        Element id= doc.createElement("mix:ImageData");
        ccs.appendChild(id);
        Element isr=doc.createElement("mix:isoSpeedRatings");
        if(ISOspeedratings!=null)
            isr.appendChild(doc.createTextNode(ISOspeedratings));
        id.appendChild(isr);
        Element ev =doc.createElement("mix:exifVersion");
        if(Exifversion!=null)
            ev.appendChild(doc.createTextNode(Exifversion));
        id.appendChild(ev);
        Element ssv = doc.createElement("mix:shutterSpeedValue");
        if(ShutterSpeedValue!=null)
            ssv.appendChild(doc.createTextNode(ShutterSpeedValue));
        id.appendChild(ssv);
        Element av = doc.createElement("mix:apertureValue");
        if(ApertureValue!=null)
            av.appendChild(doc.createTextNode(ApertureValue));
        id.appendChild(av);
        Element ebv = doc.createElement("mix:exposureBiasValue");
        if(ExposureBiasValue!=null)
            ebv.appendChild(doc.createTextNode(ExposureBiasValue));
        id.appendChild(ebv);
        Element mm = doc.createElement("mix:meteringMode");
        if(MeteringMode!=null)
            mm.appendChild(doc.createTextNode(MeteringMode));
        id.appendChild(mm);
        Element fl = doc.createElement("mix:flash");
        if(Flash!=null)
            fl.appendChild(doc.createTextNode(Flash));
        id.appendChild(fl);
        Element fol =doc.createElement("mix:focalLength");
        if(FocalLength!=null)
            fol.appendChild(doc.createTextNode(FocalLength));
        id.appendChild(fol);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

//initialize StreamResult with File object to save to file
        java.io.File f = new java.io.File(Path+"\\TIFFJPEG2K\\METADATA\\mix\\tiffmix"+i+".mix");
        StreamResult result = new StreamResult(f);
DOMSource source = new DOMSource(doc);
transformer.transform(source, result);

    }
    catch(Exception e)
    {
        System.out.println("Exception in createMIX "+e);
        e.printStackTrace();
    }
}
private void loadRawImg(java.io.File rawf)
    {
    try
    {
    Iterator readers = ImageIO.getImageReadersByFormatName("cr2");
        ImageReader reader = (ImageReader)readers.next();
        String readFormats[] = ImageIO.getReaderMIMETypes();
        System.out.println(Arrays.asList(readFormats));
       rawimg= ImageIO.read(rawf);
        }
    catch(Exception e)
    {
        System.out.println("Exception in loadRawImg"+e);
    }
}
private void loadJp2Img(java.io.File jpegf)
    {
    try
    {
    Iterator readers = ImageIO.getImageReadersByFormatName("jpeg2000");
        ImageReader reader = (ImageReader)readers.next();
        String readFormats[] = ImageIO.getReaderMIMETypes();
        System.out.println(Arrays.asList(readFormats));
       jp2img= ImageIO.read(jpegf);
        }
    catch(Exception e)
    {
        System.out.println("Exception in loadJp2Img"+e);
    }
}
private void loadTifImg(java.io.File Tiff)
    {
    try
    {
    Iterator readers = ImageIO.getImageReadersByFormatName("tif");
        ImageReader reader = (ImageReader)readers.next();
        String readFormats[] = ImageIO.getReaderMIMETypes();
        System.out.println(Arrays.asList(readFormats));
       tiffimg= ImageIO.read(Tiff);
        }
    catch(Exception e)
    {
        System.out.println("Exception in loadTifImg"+e);
    }
}
}
class Tunnel {
Connection con = null;
Session session=null;
 public void go() {

      int aport=0;
     try{
 String host="gwdspace.wrlc.org";
 String user="dspace";
 String password="GwDSpace3918";
 int port=9999;

 int tunnelLocalPort=8334;
 String tunnelRemoteHost="gwdspace.wrlc.org";
 int tunnelRemotePort=3306;

 JSch jsch=new JSch();
 session=jsch.getSession(user, host, port);
 java.util.Properties config = new java.util.Properties();
config.put("StrictHostKeyChecking", "no");
session.setConfig(config);
System.out.println(session.getUserName());
 session.setPassword(password);
 //localUserInfo lui=new localUserInfo();
 //session.setUserInfo(lui);
 session.connect();
 aport=session.setPortForwardingL(tunnelLocalPort,host,tunnelRemotePort);
 System.out.println(session.getPortForwardingL());

 System.out.println("Connected "+aport);
 Class.forName("com.mysql.jdbc.Driver");
   String url = "jdbc:mysql://127.0.0.1:8334/bagdb";

   // Get a connection to the database for a
   // user named auser with the password
   // drowssap, which is password spelled
   // backwards.


  // DriverManager.setLoginTimeout(1);
    System.out.println(DriverManager.getLoginTimeout());
  con = DriverManager.getConnection(url, "bagit","GWbaGit");
 //Thread.sleep(10000);

     }
     catch(Exception e)
     {
         System.out.println(e);
     }
    }
public void insert(String barcode,String path,String p_name,String bag_name,String status,String bag_type)
    {
try {


   Statement st = con.createStatement();
   System.out.println("result set aquired");
   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    String current= dateFormat.format(date);
    current = "'"+current+"'";

  int rs = st.executeUpdate("insert into bag_record (item_barcode,asset_store_path,project_name,bag_name,bag_status,bag_type,created_on) values "+ "("+barcode+","+path+","+p_name+","+bag_name+","+status+","+bag_type+","+current+" )");

  } catch (Exception e) {
   e.printStackTrace();
   System.out.println("Exception: " + e.getMessage());
  } }
public void done()
    {
    try
    {
    con.close();
    session.disconnect();
        }
    catch(Exception e)
    {
        System.out.println(e);
        e.printStackTrace();
    }
    finally {
   try {
    if (con != null)
     con.close();
   } catch (SQLException e) {


      }}
}
}
