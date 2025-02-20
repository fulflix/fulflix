#!/bin/bash

modules=("auth-app" "company-app" "delivery-app" "hub-app" "order-app" "product-app" "user-app")
total_designed_apis=74

total_get_count=0
total_post_count=0
total_put_count=0
total_delete_count=0

echo "Fulflix API Count Summary"
echo "=========================="

for module in "${modules[@]}"; do
  get_count=$(grep -r "@GetMapping" ./service/$module | wc -l)
  post_count=$(grep -r "@PostMapping" ./service/$module | wc -l)
  put_count=$(grep -r "@PutMapping" ./service/$module | wc -l)
  delete_count=$(grep -r "@DeleteMapping" ./service/$module | wc -l)

  module_total=$((get_count + post_count + put_count + delete_count))

  if [ $module_total -gt 0 ]; then
    echo "Module: [$module] - Total APIs: $module_total"
    [ $get_count -gt 0 ] && echo "  ├── GET: $get_count"
    [ $post_count -gt 0 ] && echo "  ├── POST: $post_count"
    [ $put_count -gt 0 ] && echo "  ├── PUT: $put_count"
    [ $delete_count -gt 0 ] && echo "  └── DELETE: $delete_count"
  fi

  total_get_count=$((total_get_count + get_count))
  total_post_count=$((total_post_count + post_count))
  total_put_count=$((total_put_count + put_count))
  total_delete_count=$((total_delete_count + delete_count))
done

total_api_count=$((total_get_count + total_post_count + total_put_count + total_delete_count))
implementation_rate=$(awk "BEGIN {printf \"%.2f\", ($total_api_count / $total_designed_apis) * 100}")

echo "=========================="
echo "Overall API Count"
echo "=========================="
echo "Total APIs: $total_api_count"
echo "  ├── GET: $total_get_count"
echo "  ├── POST: $total_post_count"
echo "  ├── PUT: $total_put_count"
echo "  └── DELETE: $total_delete_count"
echo "=========================="
echo "Implementation Rate: $implementation_rate% of $total_designed_apis designed APIs"
