import os
import hashlib
from PIL import Image
import shutil

def compute_hash(file_path):
    with open(file_path, 'rb') as f:
        content = f.read()
        hash_value = hashlib.md5(content).hexdigest()
    return hash_value

def compress_and_save(source_dir, target_dir, hash_dir):
    if not os.path.exists(target_dir):
        os.makedirs(target_dir)

    if not os.path.exists(hash_dir):
        os.makedirs(hash_dir)

    processed_files = set()

    for root, dirs, files in os.walk(source_dir):
        for file_name in files:
            source_path = os.path.join(root, file_name)
            relative_path = os.path.relpath(source_path, source_dir)
            target_path = os.path.join(target_dir, relative_path)
            hash_file_path = os.path.join(hash_dir, relative_path + '.hash')

            processed_files.add(relative_path)

            original_hash = compute_hash(source_path)

            if not os.path.exists(hash_file_path) or open(hash_file_path, 'r').read() != original_hash:
                os.makedirs(os.path.dirname(target_path), exist_ok=True)

                if file_name.lower().endswith('.png'):
                    with Image.open(source_path) as img:
                        img = img.convert('RGBA')
                        img.save(target_path, format='PNG', optimize=True)
                else:
                    shutil.copyfile(source_path, target_path)

                os.makedirs(os.path.dirname(hash_file_path), exist_ok=True)
                with open(hash_file_path, 'w') as hash_file:
                    hash_file.write(original_hash)

    for root, dirs, files in os.walk(target_dir, topdown=False):
        for file_name in files:
            file_path = os.path.join(root, file_name)
            relative_path = os.path.relpath(file_path, target_dir)
            if relative_path not in processed_files:
                os.remove(file_path)
                hash_file_path = os.path.join(hash_dir, relative_path + '.hash')
                if os.path.exists(hash_file_path):
                    os.remove(hash_file_path)

        for dir_name in dirs:
            dir_path = os.path.join(root, dir_name)
            if not os.listdir(dir_path):
                os.rmdir(dir_path)

    for root, dirs, files in os.walk(hash_dir, topdown=False):
        for dir_name in dirs:
            dir_path = os.path.join(root, dir_name)
            if not os.listdir(dir_path):
                os.rmdir(dir_path)

source_directory_path = 'resources'
target_directory_path = 'optimizedResources'
hash_directory_path = 'hashes'

print("Optimizing images...")
compress_and_save(source_directory_path, target_directory_path, hash_directory_path)
print("Optimization complete.")