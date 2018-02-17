//
//  G01Const.swift
//  dental
//
//  Created by SPJ on 2/2/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G01Const: NSObject {
    /** Function identifier */
    public static let FUNC_IDENTIFIER                           = DomainConst.APPNAME + "g01"
    /** Path to connect with PHP server */
    public static let PATH_CUSTOMER_LIST                        = "customer/list"
    /** Path to connect with PHP server */
    public static let PATH_CUSTOMER_INFO                        = "customer/view"
    /** Path to connect with PHP server */
    public static let PATH_MEDICAL_RECORD_INFO                  = "customer/medicalRecordInfo"
    /** Path to connect with PHP server */
    public static let PATH_MEDICAL_RECORD_UPDATE                = "customer/updateMedicalRecord"

}
